package org.healthyLifestyle.authentication.common.dto;

import org.healthylifestyle.user.common.dto.UserDto;

public class AuthenticationResponse {
	private UserDto user;
	private AccessToken accessToken;
	private RefreshTokenDto refreshToken;

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public AccessToken getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(AccessToken accessToken) {
		this.accessToken = accessToken;
	}

	public RefreshTokenDto getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(RefreshTokenDto refreshToken) {
		this.refreshToken = refreshToken;
	}

}
