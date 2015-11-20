package com.digitalbridge.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.digitalbridge.util.Constants;

/**
 * <p> SecurityConfiguration class. </p>
 *
 * @author rajakolli
 * @version 1 : 1
 */
// @EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired private MongoDBAuthenticationProvider authenticationProvider;
  /**
   * <p>
   * configureGlobal.
   * </p>
   *
   * @param auth a
   *          {@link org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder}
   *          object.
   * @throws java.lang.Exception if any.
   */
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProvider);
  }

	/** {@inheritDoc} */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/js/**", "/css/**", "/images/**");
	}

	// http://www.itpro.co.uk/databases/24738/mongodb-will-encrypt-your-data-to-protect-it-from-hackers

	/**
	 * {@inheritDoc}
	 *
	 * This section defines the user accounts which can be used for authentication as well as the roles each user has.
	 * @param http
   * @throws Exception
   * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
   */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		AccessDeniedHandler accessDeniedHandler = new MyAccessDeniedHandler();

		http.httpBasic()
				.and().authorizeRequests()
				.antMatchers(HttpMethod.GET,"/digitalbridge/search/getEncoded/**").permitAll()
				.anyRequest().fullyAuthenticated()
				.and().exceptionHandling().accessDeniedHandler(accessDeniedHandler)
				.and().csrf().disable()
				    .sessionManagement()
				        .sessionCreationPolicy(SessionCreationPolicy.STATELESS).sessionFixation().newSession()
				.and().headers().httpStrictTransportSecurity().includeSubDomains(true).maxAgeInSeconds(Constants.MAXAGE);
	}

}
