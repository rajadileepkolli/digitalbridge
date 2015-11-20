package com.digitalbridge.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import com.digitalbridge.domain.User;

/**
 * <p>
 * UserRepository interface.
 * </p>
 *
 * @author rajakolli
 * @version 1: 0
 */
@RepositoryRestResource(collectionResourceRel = "appUsers", path = "users")
@PreAuthorize("hasRole('ROLE_USER')")
public interface UserRepository extends MongoRepository<User, String> {

  /**
   * <p>findByUserName.</p>
   *
   * @param username a {@link java.lang.String} object.
   * @return a {@link com.digitalbridge.domain.User} object.
   */
  User findByUserName(String username);
  
  /**
   * <p>findByUserNameAndPassword.</p>
   *
   * @param username a {@link java.lang.String} object.
   * @param object a {@link java.lang.Object} object.
   * @return a {@link com.digitalbridge.domain.User} object.
   */
  User findByUserNameAndPassword(String username, Object object);
}
