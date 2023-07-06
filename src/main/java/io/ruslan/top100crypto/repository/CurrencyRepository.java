package io.ruslan.top100crypto.repository;

import io.ruslan.top100crypto.model.document.Currency;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CurrencyRepository extends MongoRepository<Currency, String> {
    Optional<Currency> findByCmcId(Long cmcId);

    Optional<Currency> findByShortName(String shortName);
}
