package org.healthylifestyle.app.config;

import org.healthyLifestyle.authentication.service.filter.RefreshTokenFilter;
import org.healthyLifestyle.authentication.service.oauth2.repository.JwtSecurityContextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private DefaultAuthorizationCodeTokenResponseClient authorizationCodeTokenResponseClient;
	@Autowired
	private DefaultOAuth2UserService oauth2UserService;
	@Autowired
	private JwtSecurityContextRepository contextRepository;
	@Autowired
	private RefreshTokenFilter refreshTokenFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.oauth2Login(e -> e.tokenEndpoint(o -> o.accessTokenResponseClient(authorizationCodeTokenResponseClient))
				.userInfoEndpoint(t -> t.userService(oauth2UserService)))
				.securityContext(sc -> sc.securityContextRepository(contextRepository))
				.authorizeHttpRequests(auth -> auth.requestMatchers("/login", "/signup").permitAll()
						.requestMatchers("/user/*/confirm/*").authenticated())
				.csrf(csrf -> csrf.disable()).addFilterAfter(refreshTokenFilter, SecurityContextHolderFilter.class);
		return http.build();
	}
}
