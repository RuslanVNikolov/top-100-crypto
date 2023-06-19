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
@Document(collection = "portfolio")
public class Portfolio {
    @Id
    private String id;
    @DBRef
    private User user;
    private String name;
    @DBRef
    private List<UserBalance> userBalances;

    public BigDecimal getTotalValue() {
        return userBalances.stream().map(UserBalance::getTotalValue).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotalProfit() {
        return userBalances.stream().map(UserBalance::getProfit).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2,
                RoundingMode.HALF_UP);
    }

    public BigDecimal getProfitPercentage() {
        BigDecimal totalValue = getTotalValue();
        if (totalValue.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal weightedProfitPercentageSum =
                userBalances.stream().map(ub -> ub.getProfitPercentage().multiply(ub.getTotalValue())).reduce(BigDecimal.ZERO, BigDecimal::add);

        return weightedProfitPercentageSum.divide(totalValue, 2, RoundingMode.HALF_UP);
    }
}
