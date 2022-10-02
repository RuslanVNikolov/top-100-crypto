package io.ruslan.top100crypto.model.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import static java.math.BigDecimal.ZERO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "portfolio")
public class Portfolio {
    @Id
    private String id;
    private User user;
    private String name;
    @DBRef
    private List<UserBalance> userBalances;

    public BigDecimal getTotalValue() {
        BigDecimal totalValue = ZERO;

        for (UserBalance userBalance : userBalances) {
            totalValue = totalValue.add(userBalance.getAmount().multiply(userBalance.getCurrency().getValueUsd()));
        }

        return totalValue.round(new MathContext(2));
    }

    public BigDecimal getTotalProfit() {
        BigDecimal totalProfit = ZERO;

        for (UserBalance userBalance : userBalances) {
            totalProfit = totalProfit.add(userBalance.getProfit());
        }

        return totalProfit.round(new MathContext(2));
    }
}
