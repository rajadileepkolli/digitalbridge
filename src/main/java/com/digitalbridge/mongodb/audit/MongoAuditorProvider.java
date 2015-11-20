package com.digitalbridge.mongodb.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p> MongoAuditorProvider class.</p>
 *
 * @author rajakolli
 * @version 1 : 0
 */
public class MongoAuditorProvider<T> implements AuditorAware<String> {

  /** {@inheritDoc} */
  /* (non-Javadoc)
   * @see org.springframework.data.domain.AuditorAware#getCurrentAuditor()
   */
  @Override
  public String getCurrentAuditor() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String username = null;
    if (principal instanceof UserDetails) {
      username = ((UserDetails) principal).getUsername();
    } else {
      username = principal.toString();
    }
    return username;
  }

}
