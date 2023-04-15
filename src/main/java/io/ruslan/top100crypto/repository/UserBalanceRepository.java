package io.ruslan.top100crypto.repository;

import io.ruslan.top100crypto.model.document.UserBalance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBalanceRepository extends MongoRepository<UserBalance, String> {
}
