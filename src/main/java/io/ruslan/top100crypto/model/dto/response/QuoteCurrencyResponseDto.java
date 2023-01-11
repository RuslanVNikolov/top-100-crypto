package io.ruslan.top100crypto.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuoteCurrencyResponseDto {
    private BigDecimal price;
    @JsonProperty("market_cap")
    private BigDecimal marketCap;
    @JsonProperty("percent_change_24h")
    private BigDecimal percentChange24h;
}
