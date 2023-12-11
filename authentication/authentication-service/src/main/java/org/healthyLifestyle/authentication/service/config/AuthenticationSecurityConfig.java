package org.healthyLifestyle.authentication.service.config;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.healthyLifestyle.authentication.service.converter.impl.CustomMappingJackson2HttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AuthenticationSecurityConfig {
	@Autowired
	private OAuth2AuthorizationCodeGrantRequestEntityConverter codeGrantRequestEntityConverter;
	@Autowired
	private Converter<Map<String, Object>, OAuth2AccessTokenResponse> oauth2AccessTokenResponseConverter;

	@Bean
	public PasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DefaultAuthorizationCodeTokenResponseClient authorizationCodeTokenResponseClient() {
		DefaultAuthorizationCodeTokenResponseClient authorizationCodeTokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();

		OAuth2AccessTokenResponseHttpMessageConverter accessTokenResponseHttpMessageConverter = new OAuth2AccessTokenResponseHttpMessageConverter();
		accessTokenResponseHttpMessageConverter.setAccessTokenResponseConverter(oauth2AccessTokenResponseConverter);

		authorizationCodeTokenResponseClient.setRequestEntityConverter(codeGrantRequestEntityConverter);
		authorizationCodeTokenResponseClient.setRestOperations(new RestTemplate(
				Arrays.asList(new FormHttpMessageConverter(), accessTokenResponseHttpMessageConverter)));

		return authorizationCodeTokenResponseClient;

	}

	@Bean
	public DefaultOAuth2UserService oauth2UserService() {
		DefaultOAuth2UserService oauth2UserService = new DefaultOAuth2UserService();

		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();

		messageConverters.removeIf(e -> e instanceof MappingJackson2HttpMessageConverter);
		messageConverters.add(new CustomMappingJackson2HttpMessageConverter());

		oauth2UserService.setRestOperations(restTemplate);
		return oauth2UserService;
	}
}
