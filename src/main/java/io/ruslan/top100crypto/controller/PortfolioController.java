package io.ruslan.top100crypto.controller;

import io.ruslan.top100crypto.model.document.Portfolio;
import io.ruslan.top100crypto.model.dto.request.CurrencyRequest;
import io.ruslan.top100crypto.model.dto.request.PortfolioRequest;
import io.ruslan.top100crypto.model.dto.request.TransactionRequest;
import io.ruslan.top100crypto.model.dto.request.UserBalanceRequest;
import io.ruslan.top100crypto.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@RequiredArgsConstructor
@RestController
@RequestMapping("/portfolios")
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping("/{id}")
    public Portfolio getPortfolioById(@PathVariable String id) {
        return portfolioService.getPortfolioById(id);
    }

    @GetMapping("/test")
    public void getPortfolioById() {
        CurrencyRequest currencyRequest = CurrencyRequest.builder()
                .id("63b58d4be52d121647f4dff8").cmcId("1").name("Bitcoin").shortName("BTC").build();
        TransactionRequest buyTransactionRequest = TransactionRequest.builder().currency(currencyRequest)
                .amount(new BigDecimal("1.5")).priceUsd(new BigDecimal("10000"))
                .date(LocalDateTime.now().minus(2, DAYS)).build();
        TransactionRequest sellTransactionRequest = TransactionRequest.builder().currency(currencyRequest)
                .amount(new BigDecimal("-1")).priceUsd(new BigDecimal("20000"))
                .date(LocalDateTime.now().minus(1, DAYS)).build();
        UserBalanceRequest userBalanceRequest = UserBalanceRequest.builder().currency(currencyRequest)
                .transactions(List.of(buyTransactionRequest, sellTransactionRequest)).build();
        PortfolioRequest portfolioRequest =
                PortfolioRequest.builder().name("My Portfolio").userBalances(List.of(userBalanceRequest)).build();
        portfolioService.save(portfolioRequest);
    }

    @PutMapping
    public Portfolio save(@RequestBody PortfolioRequest request) {
        return portfolioService.save(request);
    }
}
