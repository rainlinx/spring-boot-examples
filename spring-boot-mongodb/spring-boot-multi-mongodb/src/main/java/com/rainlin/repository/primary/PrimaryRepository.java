package com.rainlin.repository.primary;

import com.rainlin.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author neo
 */
public interface PrimaryRepository extends MongoRepository<User, String> {
}
