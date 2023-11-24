package org.healthyLifestyle.authentication.common.dto;

import org.shop.authentication.model.RefreshToken;
import org.shop.user.model.User;

public class AuthenticationResult {
	private User user;
	private AccessToken accessToken;
	private RefreshToken refreshToken;

	public AuthenticationResult(User user, AccessToken accessToken, RefreshToken refreshToken) {
		super();
		this.user = user;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public AccessToken getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(AccessToken accessToken) {
		this.accessToken = accessToken;
	}

	public RefreshToken getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(RefreshToken refreshToken) {
		this.refreshToken = refreshToken;
	}

}
