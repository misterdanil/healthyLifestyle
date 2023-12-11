package org.healthyLifestyle.authentication.web.controller;

import org.healthyLifestyle.authentication.common.dto.AuthenticationRequest;
import org.healthyLifestyle.authentication.common.dto.SignUpRequest;
import org.healthyLifestyle.authentication.service.AuthenticationService;
import org.healthyLifestyle.authentication.service.error.UnknownUserException;
import org.healthylifestyle.common.dto.ErrorResult;
import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.common.web.ResponseEntityResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
		}

		return ResponseEntity.ok().build();
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			authenticationService.signUp(signUpRequest, request, response);
		} catch (ValidationException e) {

			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
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

	@DeleteMapping("/user/*/confirm/{code}")
	public ResponseEntity<ErrorResult> confirm(@PathVariable String code) {
		try {
			authenticationService.confirmAccount(code);
		} catch (ValidationException e) {
			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
		}

		return ResponseEntity.status(204).build();
	}
}
