package org.healthyLifestyle.authentication.service;

import org.healthyLifestyle.authentication.common.dto.AuthenticationRequest;
import org.healthyLifestyle.authentication.common.dto.SignUpRequest;
import org.healthyLifestyle.authentication.service.error.UnknownUserException;
import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.user.service.error.OAuth2UserExistException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
	public void login(AuthenticationRequest authenticationRequest, HttpServletRequest request,
			HttpServletResponse response) throws ValidationException;

	public void signUp(SignUpRequest signUpRequest, HttpServletRequest request, HttpServletResponse response)
			throws ValidationException;

	public void signUp(SignUpRequest signUpRequest, String resourceId, String resourceName, HttpServletRequest request,
			HttpServletResponse response) throws OAuth2UserExistException, ValidationException;

	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws UnknownUserException;

	void confirmAccount(String code) throws ValidationException;
}
