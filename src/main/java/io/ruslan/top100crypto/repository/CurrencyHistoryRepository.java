package io.ruslan.top100crypto.repository;

import io.ruslan.top100crypto.model.document.CurrencyHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.util.List;

public interface CurrencyHistoryRepository extends MongoRepository<CurrencyHistory, String> {
    List<CurrencyHistory> findByCmcIdAndTimestampBetween(Long cmcId, Instant startDate, Instant endDate);
}
