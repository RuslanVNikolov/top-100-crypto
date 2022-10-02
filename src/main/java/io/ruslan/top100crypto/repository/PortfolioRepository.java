package io.ruslan.top100crypto.repository;

import io.ruslan.top100crypto.model.document.Portfolio;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PortfolioRepository extends MongoRepository<Portfolio, String> {

    Portfolio findByIdAndUserId(String id, String userId);
}
