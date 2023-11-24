package org.healthyLifestyle.authentication.common.dto;

import java.util.Date;

public class AccessToken {
	private String token;
	private Date issuedAt;
	private Date expiredAt;

	public AccessToken(String token, Date issuedAt, Date expiredAt) {
		super();
		this.token = token;
		this.issuedAt = issuedAt;
		this.expiredAt = expiredAt;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getIssuedAt() {
		return issuedAt;
	}

	public void setIssuedAt(Date issuedAt) {
		this.issuedAt = issuedAt;
	}

	public Date getExpiredAt() {
		return expiredAt;
	}

	public void setExpiredAt(Date expiredAt) {
		this.expiredAt = expiredAt;
	}

}
