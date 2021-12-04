package com.rainlin.repository.secondary;

import com.rainlin.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author neo
 */
public interface SecondaryRepository extends MongoRepository<User, String> {
}
