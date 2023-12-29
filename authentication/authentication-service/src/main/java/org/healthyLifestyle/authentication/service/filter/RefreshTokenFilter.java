package org.healthyLifestyle.authentication.service.filter;

import java.io.IOException;

import org.healthyLifestyle.authentication.service.RefreshTokenService;
import org.healthyLifestyle.authentication.service.oauth2.repository.JwtSecurityContextRepository;
import org.healthyLifestyle.authentication.service.provider.impl.AccessTokenProvider;
import org.healthyLifestyle.authentication.service.provider.impl.RefreshTokenProvider;
import org.healthylifestyle.user.model.User;
import org.healthylifestyle.user.service.util.RoleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RefreshTokenFilter extends OncePerRequestFilter {
	@Autowired
	private SecurityContextRepository contextRepository;
	@Autowired
	private RefreshTokenProvider refreshTokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated()) {
			filterChain.doFilter(request, response);

			return;
		}

		String token = JwtSecurityContextRepository.getToken("refresh_token", request);

		if (token != null) {
			User user = refreshTokenProvider.decryptToken(token);
			if (user == null) {
				filterChain.doFilter(request, response);

				return;
			}

			Authentication authentication = new UsernamePasswordAuthenticationToken(user.getId(), user.getPassword(),
					RoleUtil.getAuthorities(user));
			SecurityContext securityContext = new SecurityContextImpl(authentication);

			contextRepository.saveContext(securityContext, request, response);

			SecurityContextHolder.setContext(securityContext);
		}

		filterChain.doFilter(request, response);
	}

}
