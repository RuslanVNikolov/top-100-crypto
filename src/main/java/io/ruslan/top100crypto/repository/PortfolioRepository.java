package io.ruslan.top100crypto.repository;

import io.ruslan.top100crypto.model.document.Portfolio;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PortfolioRepository extends MongoRepository<Portfolio, String> {

    Optional<Portfolio> findByIdAndUserId(String id, String userId);
}
