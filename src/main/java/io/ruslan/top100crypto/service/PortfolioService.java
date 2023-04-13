package io.ruslan.top100crypto.service;

import io.ruslan.top100crypto.model.document.*;
import io.ruslan.top100crypto.model.dto.request.PortfolioRequest;
import io.ruslan.top100crypto.model.dto.request.TransactionRequest;
import io.ruslan.top100crypto.model.dto.request.UserBalanceRequest;
import io.ruslan.top100crypto.repository.CurrencyRepository;
import io.ruslan.top100crypto.repository.PortfolioRepository;
import io.ruslan.top100crypto.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final CurrencyRepository currencyRepository;
    private final UserRepository userRepository;

    @SneakyThrows
    public Portfolio getPortfolioById(String id) {
        return portfolioRepository.findByIdAndUserId(id, "figure out how to get it from session data")
                .orElseThrow(() -> new Exception(String.format("Portfolio %s not found", id)));
    }

    @SneakyThrows
    public Portfolio save(PortfolioRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (!request.getUsername().equals(user.getUsername())) {
            throw new Exception("User for portfolio does not match!");
        }
        Portfolio portfolio = Portfolio.builder()
                .id(request.getId())
                .userBalances(request.getUserBalances().stream().map(ub -> mapUserBalance(user, ub)).toList())
                .name(request.getName())
                .user(user)
                .build();
        portfolio.getUserBalances().forEach(ub -> ub.setPortfolio(portfolio));
        portfolio.getUserBalances().forEach(ub -> ub.setPortfolio(portfolio));
        user.getPortfolios().removeIf(p -> p.getId().equals(portfolio.getId()));
        user.getPortfolios().add(portfolio);
        userRepository.save(user);
        return portfolioRepository.save(portfolio);
    }

    private UserBalance mapUserBalance(User user, UserBalanceRequest ub) {
        return UserBalance.builder()
                .user(user)
                .amount(ub.getAmount())
                .currency(extractCurrency(ub))
                .transactions(ub.getTransactions().stream().map(this::mapTransaction).toList())
                .build();
    }

    private Currency extractCurrency(UserBalanceRequest ub) {
        return currencyRepository.findById(ub.getCurrency().getId()).orElse(Currency.builder()
                .name(ub.getCurrency().getName())
                .shortName(ub.getCurrency().getShortName())
                .build());
    }

    private Transaction mapTransaction(TransactionRequest t) {
        return Transaction.builder()
                .id(t.getId())
                .amount(t.getAmount())
                .currency(extractCurrency(t))
                .date(t.getDate())
                .build();
    }

    private Currency extractCurrency(TransactionRequest t) {
        return currencyRepository.findById(t.getCurrency().getId()).orElse(Currency.builder()
                .name(t.getCurrency().getName())
                .shortName(t.getCurrency().getShortName())
                .build());
    }
}
