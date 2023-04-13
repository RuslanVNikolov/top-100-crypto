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

import static java.math.BigDecimal.ZERO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "user_balance")
public class UserBalance {
    @Id
    private String id;
    @DBRef
    private User user;
    @DBRef
    private Currency currency;
    @DBRef
    private Portfolio portfolio;
    @DBRef
    private List<Transaction> transactions;
    private BigDecimal amount;

    public BigDecimal getUsdValue() {
        return amount.multiply(currency.getValueUsd());
    }

    public BigDecimal getPortion() {
        return portfolio.getTotalValue().divide(getUsdValue(), RoundingMode.HALF_UP);
    }

    public BigDecimal getAverageBuyPrice() {
        return transactions.stream()
                .filter(t -> t.getAmount().compareTo(BigDecimal.ZERO) > 0)
                .map(t -> t.getPriceUsd().multiply(t.getAmount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(getAmount(), RoundingMode.HALF_UP);
    }

    public BigDecimal getProfit() {
        return getUsdValue().subtract(getTotalSpent());
    }

    private BigDecimal getTotalSpent() {
        return transactions.stream()
                .map(t -> t.getPriceUsd().multiply(t.getAmount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
