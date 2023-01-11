package io.ruslan.top100crypto.converter;

import io.ruslan.top100crypto.model.document.Currency;
import io.ruslan.top100crypto.model.dto.response.LatestResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CurrencyMapper {

    public List<Currency> dtoToDocuments(LatestResponseDto dto) {
        List<Currency> result = new ArrayList<>();
        dto.getData().forEach(
                data -> {
                    Currency currency = new Currency();
                    currency.setValueUsd(data.getQuote().getUsd().getPrice());
                    currency.setName(data.getName());
                    currency.setShortName(data.getSymbol());
                    currency.setCmcId(data.getId());
                    currency.setChange24h(data.getQuote().getUsd().getPercentChange24h());
                    currency.setMarketCap(data.getQuote().getUsd().getMarketCap());
                    result.add(currency);
                }
        );

        return result;
    }
}
