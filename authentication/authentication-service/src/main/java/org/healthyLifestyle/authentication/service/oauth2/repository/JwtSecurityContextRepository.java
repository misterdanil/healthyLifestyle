package org.shop.authentication.service.oauth2.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.shop.authentication.common.dto.AccessToken;
import org.shop.authentication.common.dto.SignUpRequest;
import org.shop.authentication.model.RefreshToken;
import org.shop.authentication.service.RefreshTokenService;
import org.shop.authentication.service.provider.impl.AccessTokenProvider;
import org.shop.authentication.service.provider.impl.RefreshTokenProvider;
import org.shop.user.model.User;
import org.shop.user.service.UserService;
import org.shop.user.service.error.OAuth2UserExistException;
import org.shop.user.service.util.RoleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Repository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Repository
public class JwtSecurityContextRepository implements SecurityContextRepository {
	@Autowired
	private AccessTokenProvider accessTokenProvider;
	@Autowired
	private RefreshTokenProvider refreshTokenProvider;
	@Autowired
	private UserService userService;
	@Autowired
	private RefreshTokenService refreshTokenService;

	private static final String ACCESS_TOKEN_NAME = "access_token";
	private static final String REFRESH_TOKEN_NAME = "refresh_token";

	private Logger logger = LoggerFactory.getLogger(JwtSecurityContextRepository.class);

	@Override
	public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
		HttpServletRequest request = requestResponseHolder.getRequest();
		String token = getToken(ACCESS_TOKEN_NAME, request);

		if (token != null) {
			User user = accessTokenProvider.decryptToken(token);

			Authentication authentication = new UsernamePasswordAuthenticationToken(user.getId(), user.getPassword(),
					RoleUtil.getAuthorities(user));
			SecurityContext securityContext = new SecurityContextImpl(authentication);

			return securityContext;
		}

		token = getToken(REFRESH_TOKEN_NAME, request);
		if (token != null) {
			User user = refreshTokenProvider.decryptToken(token);

			Authentication authentication = new UsernamePasswordAuthenticationToken(user.getId(), user.getPassword());
			SecurityContext securityContext = new SecurityContextImpl(authentication);

			return securityContext;
		}

		return null;
	}

	private String getToken(String name, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		
		if(cookies == null) {
			return null;
		}
		
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				return cookie.getValue();
			}
		}

		return null;
	}

	@Override
	public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
		Authentication authentication = context.getAuthentication();

		User user;
		if (authentication instanceof OAuth2AuthenticationToken) {
			OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
			user = saveOAuth2User(authenticationToken);
		} else {
			user = userService.findById(Long.valueOf(authentication.getName()));
		}

		AccessToken accessToken = accessTokenProvider.generateToken(user);
		RefreshToken refreshToken = refreshTokenProvider.generateToken(user);
		refreshToken = refreshTokenService.save(refreshToken);

		List<Cookie> accessTokenCookies = createAccessTokenCookies(accessToken);
		List<Cookie> refreshTokenCookies = createRefreshTokenCookies(refreshToken);

		accessTokenCookies.forEach(c -> response.addCookie(c));
		refreshTokenCookies.forEach(c -> response.addCookie(c));
	}

	private User saveOAuth2User(OAuth2AuthenticationToken authenticationToken) {
		String registrationId = authenticationToken.getAuthorizedClientRegistrationId();

		User user = null;
		try {
			if (registrationId.equals("vk")) {
				user = saveVKUser(authenticationToken);
			} else if (registrationId.equals("google")) {
				user = saveGoogleUser(authenticationToken);
			} else {
				logger.info("This registration id '%s' is unknown", registrationId);
			}
		} catch (OAuth2UserExistException e) {
			user = userService.findByOAuth2IdAndOAuth2Resource(authenticationToken.getName(), registrationId);
		}

		return user;
	}

	private List<Cookie> createAccessTokenCookies(AccessToken accessToken) {
		List<Cookie> cookies = new ArrayList<>();

		int duration = (int) ChronoUnit.SECONDS.between(accessToken.getExpiredAt().toInstant(),
				accessToken.getIssuedAt().toInstant());

		Cookie valueCookie = new Cookie("access_token", accessToken.getToken());
		valueCookie.setMaxAge(duration);
		valueCookie.setHttpOnly(true);

		Cookie expiresAtCookie = new Cookie("at_expires_at", String.valueOf(accessToken.getExpiredAt().getTime()));
		expiresAtCookie.setMaxAge(duration);

		cookies.add(valueCookie);
		cookies.add(expiresAtCookie);

		return cookies;
	}

	private List<Cookie> createRefreshTokenCookies(RefreshToken refreshToken) {
		List<Cookie> cookies = new ArrayList<>();

		int duration = (int) ChronoUnit.SECONDS.between(refreshToken.getExpiredAt().toInstant(),
				refreshToken.getIssuedAt().toInstant());

		Cookie valueCookie = new Cookie("refresh_token", refreshToken.getToken());
		valueCookie.setMaxAge(duration);
		valueCookie.setHttpOnly(true);

		Cookie expiresAtCookie = new Cookie("rt_expires_at", String.valueOf(refreshToken.getExpiredAt().getTime()));
		expiresAtCookie.setMaxAge(duration);

		cookies.add(valueCookie);
		cookies.add(expiresAtCookie);

		return cookies;
	}

	private User saveVKUser(OAuth2AuthenticationToken authenticationToken) throws OAuth2UserExistException {
		Map<String, Object> attributes = authenticationToken.getPrincipal().getAttributes();
		String id = authenticationToken.getName();
		String firstName = (String) attributes.get("first_name");
		String lastName = (String) attributes.get("last_name");
		String email = (String) attributes.get("email");

		String birthDateStr = (String) attributes.get("bdate");
		Date birthDate;
		try {
			birthDate = new SimpleDateFormat("d.M.yyyy").parse(birthDateStr);
		} catch (ParseException e) {
			throw new RuntimeException(
					String.format("Exception occurred while parsing vk birth date. Couldn't parse '%s'", birthDateStr),
					e);
		}

		SignUpRequest signUpRequest = new SignUpRequest();
		signUpRequest.setFirstName(firstName);
		signUpRequest.setLastName(lastName);
		signUpRequest.setEmail(email);
		signUpRequest.setBirthDate(birthDate.toInstant());

		User user = userService.save(signUpRequest, id, authenticationToken.getAuthorizedClientRegistrationId());

		return user;
	}

	private User saveGoogleUser(OAuth2AuthenticationToken authenticationToken) throws OAuth2UserExistException {
		Map<String, Object> attributes = authenticationToken.getPrincipal().getAttributes();
		String sub = authenticationToken.getName();
		String firstName = (String) attributes.get("given_name");
		String lastName = (String) attributes.get("family_name");
		String email = (String) attributes.get("email");

		SignUpRequest signUpRequest = new SignUpRequest();
		signUpRequest.setFirstName(firstName);
		signUpRequest.setLastName(lastName);
		signUpRequest.setEmail(email);

		User user = userService.save(signUpRequest, sub, authenticationToken.getAuthorizedClientRegistrationId());

		return user;
	}

	@Override
	public boolean containsContext(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();

		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals("access_token")) {
				return true;
			}
		}

		return false;
	}

}
