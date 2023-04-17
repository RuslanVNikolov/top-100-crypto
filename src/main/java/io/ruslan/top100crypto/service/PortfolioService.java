package io.ruslan.top100crypto.service;

import io.ruslan.top100crypto.model.document.*;
import io.ruslan.top100crypto.model.dto.request.PortfolioRequest;
import io.ruslan.top100crypto.model.dto.request.TransactionRequest;
import io.ruslan.top100crypto.model.dto.request.UserBalanceRequest;
import io.ruslan.top100crypto.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final CurrencyRepository currencyRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final UserBalanceRepository userBalanceRepository;

    @SneakyThrows
    public Portfolio getUserPortfolio() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return portfolioRepository.findAllByUserId(user.getId()).stream().min(Comparator.comparing(Portfolio::getTotalValue)).orElse(null);
    }

    @SneakyThrows
    public Portfolio save(PortfolioRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        List<UserBalance> userBalances =
                userBalanceRepository.saveAll(request.getUserBalances().stream().map(this::mapUserBalance).toList());
        Portfolio portfolio = Portfolio.builder()
                .id(request.getId())
                .userBalances(userBalances)
                .name(request.getName())
                .user(user)
                .build();

        return portfolioRepository.save(portfolio);
    }

    private UserBalance mapUserBalance(UserBalanceRequest ub) {
        List<Transaction> transactions =
                transactionRepository.saveAll(ub.getTransactions().stream().map(this::mapTransaction).toList());
        return UserBalance.builder()
                .currency(extractCurrency(ub))
                .transactions(transactions)
                .build();
    }

    private Currency extractCurrency(UserBalanceRequest ub) {
        return currencyRepository.findById(ub.getCurrency().getId()).orElse(currencyRepository.save(Currency.builder()
                .name(ub.getCurrency().getName())
                .shortName(ub.getCurrency().getShortName())
                .build()));
    }

    private Transaction mapTransaction(TransactionRequest t) {
        return Transaction.builder()
                .id(t.getId())
                .amount(t.getAmount())
                .priceUsd(t.getPriceUsd())
                .date(t.getDate())
                .build();
    }
}
