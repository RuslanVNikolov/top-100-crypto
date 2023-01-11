package io.ruslan.top100crypto.service;

import io.ruslan.top100crypto.model.document.Currency;
import io.ruslan.top100crypto.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RequiredArgsConstructor
@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public List<Currency> getTop100Currencies() {
        return currencyRepository.findAll(PageRequest.of(0, 100, Sort.by(DESC, "marketCap"))).getContent();
    }

    public void upsertAll(List<Currency> currencies) {
        currencies.forEach(c -> {
            Optional<Currency> currencyOptional = currencyRepository.findByCmcId(c.getCmcId());

            if(currencyOptional.isPresent()) {
                Currency currency = currencyOptional.get();
                currency.setName(c.getName());
                currency.setMarketCap(c.getMarketCap());
                currency.setShortName(c.getShortName());
                currency.setValueUsd(c.getValueUsd());
                currencyRepository.save(currency);
            } else {
                currencyRepository.save(c);
            }
        });
    }
}
