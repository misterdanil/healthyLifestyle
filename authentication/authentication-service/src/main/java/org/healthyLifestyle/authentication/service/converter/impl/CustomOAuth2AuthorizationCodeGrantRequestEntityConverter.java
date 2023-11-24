package org.shop.authentication.service.converter.impl;

import java.net.URI;

import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class CustomOAuth2AuthorizationCodeGrantRequestEntityConverter
		extends OAuth2AuthorizationCodeGrantRequestEntityConverter {

	@Override
	public RequestEntity<?> convert(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
		ClientRegistration clientRegistration = authorizationGrantRequest.getClientRegistration();
		if (clientRegistration.getRegistrationId().equals("vk")) {
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			params.add("client_id", clientRegistration.getClientId());
			params.add("client_secret", clientRegistration.getClientSecret());
			params.add("redirect_uri", clientRegistration.getRedirectUri());
			params.add("code",
					authorizationGrantRequest.getAuthorizationExchange().getAuthorizationResponse().getCode());

			URI uri = UriComponentsBuilder
					.fromUriString(authorizationGrantRequest.getClientRegistration().getProviderDetails().getTokenUri())
					.queryParams(params).build().toUri();

			return new RequestEntity<>(HttpMethod.POST, uri);
		}
		return super.convert(authorizationGrantRequest);
	}

}
