package com.digitalbridge.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.digitalbridge.mongodb.repository.UserRepository;
import com.digitalbridge.util.KeyGeneratorUtil;

/**
 * <p> MongoDBAuthenticationProvider class. </p>
 *
 * @author rajakolli
 * @version 1 : 0
 */
@Service
public class MongoDBAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

  @Autowired UserRepository userRepository;

  /** {@inheritDoc} */
  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails,
      UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    /** Nothing to do as we are not going to do additional Checks */
  }

  /** {@inheritDoc} */
  @Override
  protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
      throws AuthenticationException {

    UserDetails loadedUser = null;

    try {
      com.digitalbridge.domain.User dbUser = userRepository.findByUserNameAndPassword(username,
          KeyGeneratorUtil.encrypt(authentication.getCredentials().toString()));
      if (dbUser != null) {
        loadedUser = new org.springframework.security.core.userdetails.User(dbUser.getUserName(), dbUser.getPassword(),
            dbUser.getRoles());
      }
    } catch (Exception repositoryProblem) {
      throw new InternalAuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
    }

    if (loadedUser == null) {
      throw new InternalAuthenticationServiceException(
          "UserDetailsService returned null, which is an interface contract violation");
    }

    return loadedUser;
  }

}
