package org.healthyLifestyle.authentication.common.dto;

import jakarta.validation.constraints.Pattern;

public class AuthenticationRequest {
	@Pattern(regexp = "")
	private String email;
	@Pattern(regexp = "^[A-Za-z0-9]{8, }$")
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
