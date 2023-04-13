package io.ruslan.top100crypto.model.dto.request;

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
public class CurrencyRequest {
    private String id;
    private String cmcId;
    private String name;
    private String shortName;
}
