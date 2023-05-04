package io.ruslan.top100crypto.service;

import io.ruslan.top100crypto.model.document.Currency;
import io.ruslan.top100crypto.model.document.CurrencyHistory;
import io.ruslan.top100crypto.repository.CurrencyHistoryRepository;
import io.ruslan.top100crypto.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RequiredArgsConstructor
@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyHistoryRepository currencyHistoryRepository;

    public List<Currency> getTop100Currencies() {
        return currencyRepository.findAll(PageRequest.of(0, 100, Sort.by(DESC, "marketCap"))).getContent();
    }

    public List<CurrencyHistory> getHistoryForCurrency(Long cmcId, Instant from, Instant to) {
        return currencyHistoryRepository.findByCmcIdAndTimestampBetween(cmcId, from, to);
    }

    public void upsertAll(List<Currency> currencies) {
        currencies.forEach(c -> {
            Optional<Currency> currencyOptional = currencyRepository.findByCmcId(c.getCmcId());

            if (currencyOptional.isPresent()) {
                Currency current = currencyOptional.get();
                CurrencyHistory archive = CurrencyHistory.builder()
                        .cmcId(c.getCmcId())
                        .valueUsd(c.getValueUsd())
                        .timestamp(Instant.now())
                        .build();
                currencyHistoryRepository.save(archive);
                current.setName(c.getName());
                current.setMarketCap(c.getMarketCap());
                current.setShortName(c.getShortName());
                current.setValueUsd(c.getValueUsd());
                currencyRepository.save(current);
            } else {
                currencyRepository.save(c);
            }
        });
    }
}
