package io.ruslan.top100crypto.model.dto.response;

import io.ruslan.top100crypto.model.document.Portfolio;
import io.ruslan.top100crypto.model.document.UserBalance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBalanceResponseDto {
    private CurrencyResponseDto currency;
    private List<TransactionResponseDto> transactions = new ArrayList<>();
    private BigDecimal percentage;
    private BigDecimal profit;
    private BigDecimal profitPercentage;
    private BigDecimal averageBuyPrice;

    public UserBalanceResponseDto(UserBalance userBalance, Portfolio portfolio) {
        this.currency = new CurrencyResponseDto(userBalance.getCurrency());
        this.transactions = userBalance.getTransactions().stream()
                .map(TransactionResponseDto::new)
                .collect(Collectors.toList());
        this.percentage = userBalance.getPercentageFromPortfolio(portfolio);
        this.profit = userBalance.getProfit();
        this.profitPercentage = userBalance.getProfitPercentage();
        this.averageBuyPrice = userBalance.getAverageBuyPrice();
    }
}
