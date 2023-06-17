package io.ruslan.top100crypto.model.dto.response;

import io.ruslan.top100crypto.model.document.Portfolio;
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
public class PortfolioResponseDto {
    private List<UserBalanceResponseDto> userBalances = new ArrayList<>();
    private BigDecimal profit;
    private BigDecimal profitPercentage;
    private BigDecimal totalValue;
    private String name;

    public PortfolioResponseDto(Portfolio portfolio) {
        this.userBalances = portfolio.getUserBalances().stream()
                .map(ub -> new UserBalanceResponseDto(ub, portfolio))
                .collect(Collectors.toList());
        this.profit = portfolio.getTotalProfit();
        this.profitPercentage = portfolio.getProfitPercentage();
        this.totalValue = portfolio.getTotalValue();
        this.name = portfolio.getName();
    }
}
