package com.digitalbridge.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

/**
 * <p>
 * SecurityUtils class.
 * </p>
 *
 * @author rajakolli
 * @version 1: 0
 */
public class SecurityUtils {
	
	private SecurityUtils(){
		
	}
	
	/**
	 * Configures the Spring Security {@link org.springframework.security.core.context.SecurityContext} to be authenticated as the user with the given username and
	 * password as well as the given granted authorities.
	 *
	 * @param username must not be {@literal null} or empty.
	 * @param password must not be {@literal null} or empty.
	 * @param roles a {@link java.lang.String} object.
	 */
	public static void runAs(String username, String password, String... roles) {

		Assert.notNull(username, "Username must not be null!");
		Assert.notNull(password, "Password must not be null!");

		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(username, password, AuthorityUtils.createAuthorityList(roles)));
	}
}
