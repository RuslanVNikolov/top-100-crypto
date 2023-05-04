package io.ruslan.top100crypto.model.document;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "currency_history")
public class CurrencyHistory {

    @Id
    private String id;
    private Long cmcId;
    private Instant timestamp;
    private BigDecimal valueUsd;
}
