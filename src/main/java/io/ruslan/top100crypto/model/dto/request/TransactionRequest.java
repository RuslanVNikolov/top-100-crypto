package io.ruslan.top100crypto.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    private String symbol;
    private BigDecimal amount;
    private BigDecimal price;
    private LocalDateTime date;
}
