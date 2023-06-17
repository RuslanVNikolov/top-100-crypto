package io.ruslan.top100crypto.model.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "user_balance")
public class UserBalance {
    @Id
    private String id;
    @DBRef
    private Currency currency;
    @DBRef
    private List<Transaction> transactions;

    public BigDecimal getTotalAmount() {
        return transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalValue() {
        return getTotalAmount().multiply(currency.getValueUsd());
    }

    public BigDecimal getAverageBuyPrice() {
        List<Transaction> buyTransactions = transactions.stream()
                .filter(t -> t.getAmount().compareTo(BigDecimal.ZERO) > 0).toList();

        BigDecimal totalAmountBought = buyTransactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalCostBought = buyTransactions.stream()
                .map(t -> t.getPriceUsd().multiply(t.getAmount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalCostBought.divide(totalAmountBought, RoundingMode.HALF_UP);
    }


    public BigDecimal getProfit() {
        return getTotalValue().subtract(getTotalSpent());
    }

    public BigDecimal getProfitPercentage() {
        BigDecimal totalSpent = getTotalSpent();
        BigDecimal profit = getProfit();

        // To avoid divide by zero error
        if (totalSpent.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return profit.divide(totalSpent, RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
    }

    public BigDecimal getTotalSpent() {
        return transactions.stream()
                .map(t -> t.getPriceUsd().multiply(t.getAmount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getPercentageFromPortfolio(Portfolio portfolio) {
        return getTotalValue().divide(portfolio.getTotalValue(), RoundingMode.HALF_DOWN);
    }
}
