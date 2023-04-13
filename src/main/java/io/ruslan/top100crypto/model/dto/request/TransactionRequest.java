package io.ruslan.top100crypto.model.dto.request;

import io.ruslan.top100crypto.model.document.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    private String id;
    private CurrencyRequest currency;
    private String name;
    private BigDecimal amount;
    private BigDecimal priceUsd;
    private Date date;
}
