package io.ruslan.top100crypto.service;

import io.ruslan.top100crypto.model.document.*;
import io.ruslan.top100crypto.model.dto.request.PortfolioRequest;
import io.ruslan.top100crypto.model.dto.request.TransactionRequest;
import io.ruslan.top100crypto.model.dto.request.UserBalanceRequest;
import io.ruslan.top100crypto.model.dto.response.PortfolioResponseDto;
import io.ruslan.top100crypto.repository.CurrencyRepository;
import io.ruslan.top100crypto.repository.PortfolioRepository;
import io.ruslan.top100crypto.repository.TransactionRepository;
import io.ruslan.top100crypto.repository.UserBalanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final CurrencyRepository currencyRepository;
    private final TransactionRepository transactionRepository;
    private final UserBalanceRepository userBalanceRepository;

    public PortfolioResponseDto getUserPortfolio() {
        return getUserPortfolio(null);
    }

    @SneakyThrows
    private PortfolioResponseDto getUserPortfolio(PortfolioResponseDto orElse) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Optional<Portfolio> portfolioOpt =
                portfolioRepository.findAllByUserId(user.getId()).stream().min(Comparator.comparing(Portfolio::getTotalValue));
        if (portfolioOpt.isEmpty()) {
            return null;
        }

        PortfolioResponseDto wtf = new PortfolioResponseDto(portfolioOpt.get());
        log.info(wtf.toString());
        return wtf;
    }

    @SneakyThrows
    public Portfolio save(PortfolioRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        List<UserBalance> userBalances =
                userBalanceRepository.saveAll(request.getUserBalances().stream().map(this::mapUserBalance).toList());
        Portfolio portfolio =
                Portfolio.builder().id(request.getId()).userBalances(userBalances).name(request.getName()).user(user).build();

        return portfolioRepository.save(portfolio);
    }

    @SneakyThrows
    public Portfolio save(TransactionRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Currency currency =
                currencyRepository.findByShortName(request.getSymbol()).orElse(Currency.builder().name(request.getSymbol()).shortName(request.getSymbol()).build());
        currencyRepository.save(currency);

        Portfolio portfolio =
                portfolioRepository.findAllByUserId(user.getId()).stream().findFirst().orElse(Portfolio.builder().user(user).userBalances(new ArrayList<>()).build());
        Optional<UserBalance> userBalanceOpt =
                portfolio.getUserBalances().stream().filter(ub -> ub.getCurrency().getShortName().equals(currency.getShortName())).findFirst();
        Transaction transaction =
                transactionRepository.save(Transaction.builder().date(request.getDate()).priceUsd(request.getPrice()).amount(request.getAmount()).build());
        UserBalance userBalance;
        if (userBalanceOpt.isPresent()) {
            userBalance = userBalanceOpt.get();
        } else {
            userBalance = UserBalance.builder().transactions(new ArrayList<>()).currency(currency).build();
            portfolio.getUserBalances().add(userBalance);
        }

        userBalance.getTransactions().add(transaction);
        userBalanceRepository.save(userBalance);

        return portfolioRepository.save(portfolio);
    }

    private UserBalance mapUserBalance(UserBalanceRequest ub) {
        List<Transaction> transactions =
                transactionRepository.saveAll(ub.getTransactions().stream().map(this::mapTransaction).toList());
        return UserBalance.builder().currency(extractCurrency(ub)).transactions(transactions).build();
    }

    private Currency extractCurrency(UserBalanceRequest ub) {
        return currencyRepository.findById(ub.getCurrency().getId()).orElse(currencyRepository.save(Currency.builder().name(ub.getCurrency().getName()).shortName(ub.getCurrency().getShortName()).build()));
    }

    private Transaction mapTransaction(TransactionRequest t) {
        return Transaction.builder().amount(t.getAmount()).priceUsd(t.getPrice()).date(t.getDate()).build();
    }
}
