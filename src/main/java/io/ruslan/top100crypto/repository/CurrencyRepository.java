package io.ruslan.top100crypto.repository;

import io.ruslan.top100crypto.model.document.Currency;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CurrencyRepository extends MongoRepository<Currency, String> {
}
