package io.ruslan.top100crypto.repository;

import io.ruslan.top100crypto.model.document.Portfolio;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PortfolioRepository extends MongoRepository<Portfolio, String> {
    List<Portfolio> findAllByUserId(String userId);
}
