package com.digitalbridge.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * <p> MyAccessDeniedHandler class. </p>
 *
 * @author rajakolli
 * @version 1 : 0
 */
public class MyAccessDeniedHandler implements AccessDeniedHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(MyAccessDeniedHandler.class);

	/** {@inheritDoc} */
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		if (!response.isCommitted() && (!request.isSecure())) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			LOGGER.error("{} for user :: {}", accessDeniedException.getMessage(), request.getUserPrincipal().getName());
		}
	}

}
