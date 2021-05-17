package com.digitalbridge.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.digitalbridge.domain.User;

@RepositoryRestResource(collectionResourceRel = "appUsers", path = "users")
public interface UserRepository extends MongoRepository<User, String> {

	User findByUserName(String username);

	User findByUserNameAndPassword(String username, Object object);
}
