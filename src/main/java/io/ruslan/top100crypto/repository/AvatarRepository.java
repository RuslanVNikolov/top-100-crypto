package io.ruslan.top100crypto.repository;

import io.ruslan.top100crypto.model.document.Avatar;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarRepository extends MongoRepository<Avatar, String> {
}
