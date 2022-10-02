package io.ruslan.top100crypto.model.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
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
        return portfolio.getTotalValue().divide(getUsdValue());
    }

    public BigDecimal getAverageBuyPrice() {
        BigDecimal averageBuyPrice = ZERO;

        for (Transaction transaction : transactions.stream().filter(t -> t.getAmount().compareTo(ZERO) > 0).toList()) {
            averageBuyPrice = averageBuyPrice.add(transaction.getPriceUsd().multiply(transaction.getAmount()));
        }

        return averageBuyPrice.divide(amount);
    }

    public BigDecimal getProfit() {
        BigDecimal totalSpent = ZERO;

        for (Transaction transaction : transactions) {
            totalSpent = totalSpent.add(transaction.getPriceUsd().multiply(transaction.getAmount()));
        }

        return getUsdValue().subtract(totalSpent);
    }
}
