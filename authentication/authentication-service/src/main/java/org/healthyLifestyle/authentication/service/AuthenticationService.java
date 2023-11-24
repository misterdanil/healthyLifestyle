package org.shop.authentication.service;

import org.shop.authentication.common.dto.AuthenticationRequest;
import org.shop.authentication.common.dto.SignUpRequest;
import org.shop.authentication.service.error.UnknownUserException;
import org.shop.user.service.error.OAuth2UserExistException;
import org.shop.user.service.error.ValidationException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
	public void login(AuthenticationRequest authenticationRequest, HttpServletRequest request,
			HttpServletResponse response) throws ValidationException;

	public void signUp(SignUpRequest signUpRequest, HttpServletRequest request, HttpServletResponse response)
			throws ValidationException;

	public void signUp(SignUpRequest signUpRequest, String resourceId, String resourceName, HttpServletRequest request,
			HttpServletResponse response) throws OAuth2UserExistException;

	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws UnknownUserException;
}
