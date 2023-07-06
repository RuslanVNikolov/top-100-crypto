package io.ruslan.top100crypto.repository;

import io.ruslan.top100crypto.model.document.UserCurrency;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCurrencyRepository extends MongoRepository<UserCurrency, String> {
    Optional<UserCurrency> findByUserIdAndCurrencyId(String userId, String currencyId);

    List<UserCurrency> findAllByUserId(String userId);
}
