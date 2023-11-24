package org.shop.authentication.web.controller;

import org.shop.authentication.common.dto.AuthenticationRequest;
import org.shop.authentication.common.dto.SignUpRequest;
import org.shop.authentication.service.AuthenticationService;
import org.shop.authentication.service.error.UnknownUserException;
import org.shop.common.web.ErrorParser;
import org.shop.common.web.ErrorResult;
import org.shop.user.service.error.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AuthenticationController {
	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			authenticationService.login(authenticationRequest, request, response);
		} catch (ValidationException e) {
			BindingResult result = e.getResult();

			ErrorResult errorResult = ErrorParser.getErrorResult(result);

			return ResponseEntity.badRequest().body(errorResult);
		}

		return ResponseEntity.ok().build();
	}

	@PostMapping("/signup")
	public ResponseEntity<?> login(@RequestBody SignUpRequest signUpRequest, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			authenticationService.signUp(signUpRequest, request, response);
		} catch (ValidationException e) {
			BindingResult result = e.getResult();

			ErrorResult errorResult = ErrorParser.getErrorResult(result);

			return ResponseEntity.badRequest().body(errorResult);
		}

		return ResponseEntity.ok().build();
	}

	@PostMapping("/user/refresh")
	public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
		try {
			authenticationService.refreshToken(request, response);
		} catch (UnknownUserException e) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok().build();
	}
}
