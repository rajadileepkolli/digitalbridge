package com.digitalbridge.mongodb.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * <p>
 * MongoAuditorProvider class.
 * </p>
 *
 * @author rajakolli
 * @version 1 : 0
 */
public class MongoAuditorProvider<T> implements AuditorAware<String> {

	/** {@inheritDoc} */
	@Override
	public Optional<String> getCurrentAuditor() {
		Object principal = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		}
		else {
			username = principal.toString();
		}
		return Optional.of(username);
	}

}
