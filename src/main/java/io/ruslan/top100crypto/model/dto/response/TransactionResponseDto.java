package io.ruslan.top100crypto.model.dto.response;

import io.ruslan.top100crypto.model.document.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDto {
    private BigDecimal amount;
    private BigDecimal price;
    private LocalDateTime date;

    public TransactionResponseDto(Transaction transaction) {
        this.amount = transaction.getAmount();
        this.price = transaction.getPriceUsd();
        this.date = transaction.getDate();
    }
}
