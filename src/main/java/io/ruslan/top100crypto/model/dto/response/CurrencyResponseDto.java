package io.ruslan.top100crypto.model.dto.response;

import io.ruslan.top100crypto.model.document.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyResponseDto {
    private Long cmcId;
    private String name;
    private String shortName;
    private BigDecimal valueUsd;
    private BigDecimal marketCap;
    private BigDecimal change24h;

    public CurrencyResponseDto(Currency currency) {
        this.cmcId = currency.getCmcId();
        this.name = currency.getName();
        this.shortName = currency.getShortName();
        this.change24h = currency.getChange24h();
        this.valueUsd = currency.getValueUsd();
        this.marketCap = currency.getMarketCap();
    }
}
