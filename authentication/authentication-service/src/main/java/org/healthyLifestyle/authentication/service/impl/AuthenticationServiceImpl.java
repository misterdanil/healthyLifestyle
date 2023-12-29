package org.healthyLifestyle.authentication.service.impl;

import org.healthyLifestyle.authentication.common.dto.AuthenticationRequest;
import org.healthyLifestyle.authentication.common.dto.SignUpRequest;
import org.healthyLifestyle.authentication.model.ConfirmCode;
import org.healthyLifestyle.authentication.service.AuthenticationService;
import org.healthyLifestyle.authentication.service.ConfirmCodeService;
import org.healthyLifestyle.authentication.service.RefreshTokenService;
import org.healthyLifestyle.authentication.service.error.UnknownUserException;
import org.healthyLifestyle.authentication.service.provider.impl.AccessTokenProvider;
import org.healthyLifestyle.authentication.service.provider.impl.RefreshTokenProvider;
import org.healthylifestyle.common.error.BindingResultFactory;
import org.healthylifestyle.common.error.Type;
import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.common.web.ErrorParser;
import org.healthylifestyle.user.model.User;
import org.healthylifestyle.user.service.UserService;
import org.healthylifestyle.user.service.error.OAuth2UserExistException;
import org.healthylifestyle.user.service.util.RoleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
@PropertySource("auth_code.properties")
public class AuthenticationServiceImpl implements AuthenticationService {
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private ConfirmCodeService codeService;
	@Resource
	private Environment env;
	@Autowired
	private SecurityContextRepository contextRepository;
	@Autowired
	private AccessTokenProvider accessTokenProvider;
	@Autowired
	private RefreshTokenService refreshTokenService;
	@Autowired
	private RefreshTokenProvider refreshTokenProvider;
	@Autowired
	private SecurityContextRepository securityContextRepository;
	@Autowired
	private Validator validator;
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

	@Override
	public void login(AuthenticationRequest authenticationRequest, HttpServletRequest request,
			HttpServletResponse response) throws ValidationException {
		logger.debug("Start validating login request");
		BindingResult bindingResult = new BeanPropertyBindingResult(authenticationRequest, "authenticationRequest");

		String email = authenticationRequest.getEmail();
		String password = authenticationRequest.getPassword();

		User user = userService.findByEmail(email);
		if (user == null) {
			logger.debug("There is no user with email " + email);
			bindingResult.rejectValue("email", "email.not_exists", "Логин и/или пароль неверны");
			ErrorParser.checkErrors(bindingResult,
					"Exception occurred while logging user. The user doesn't exist with email '%s'", Type.NOT_FOUND,
					email);
		}

		if (!passwordEncoder.matches(password, user.getPassword())) {
			logger.debug("Passwords are mismatch");
			bindingResult.rejectValue("password", "password.mismatch", "Пароль неверный");
			ErrorParser.checkErrors(bindingResult, "The user doesn't have the same password", Type.NOT_FOUND);
		}

		logger.debug("Login data is valid. Authorization user...");

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getId(),
				user.getPassword(), RoleUtil.getAuthorities(user));
		SecurityContext securityContext = new SecurityContextImpl(authenticationToken);

		SecurityContextHolder.getContext().setAuthentication(authenticationToken);

		contextRepository.saveContext(securityContext, request, response);
	}

	@Override
	public void signUp(SignUpRequest signUpRequest, HttpServletRequest request, HttpServletResponse response)
			throws ValidationException {
		logger.debug("Start validating sign up request");
		BindingResult validationResult = BindingResultFactory.getInstance(signUpRequest, "signUpRequest", validator);

		ErrorParser.checkErrors(validationResult, "Exception occurred while signing up user. The data is invalid",
				Type.BAD_REQUEST);

		String email = signUpRequest.getEmail();

		if (userService.findByEmail(email) != null) {
			validationResult.reject("authentication.signUp.email.exist", "Пользователь с такой почтой уже существует");
		}

		ErrorParser.checkErrors(validationResult, "The user with email '%s' already exists", Type.BAD_REQUEST, email);

		logger.debug("Sign up request is valid");

		signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

		User user = userService.save(signUpRequest);

		ConfirmCode confirmCode = codeService.save(user);
		sendCode(confirmCode);

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getId(),
				user.getPassword(), RoleUtil.getAuthorities(user));
		SecurityContext securityContext = new SecurityContextImpl(authenticationToken);

		contextRepository.saveContext(securityContext, request, response);
	}

	@Override
	public void signUp(SignUpRequest signUpRequest, String resourceId, String resourceName, HttpServletRequest request,
			HttpServletResponse response) throws OAuth2UserExistException, ValidationException {
		BindingResult validationResult = BindingResultFactory.getInstance(signUpRequest, "signUpRequest", validator);

		ErrorParser.checkErrors(validationResult, "Exception occurred while signing up user. The data is invalid",
				Type.BAD_REQUEST);

		String email = signUpRequest.getEmail();

		if (userService.findByEmail(email) != null) {
			validationResult.reject("authentication.signUp.email.exist", "Пользователь с такой почтой уже существует");
		}

		ErrorParser.checkErrors(validationResult, "The user with email '%s' already exists", Type.BAD_REQUEST, email);

		signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

		User user = userService.save(signUpRequest, resourceId, resourceName);

		ConfirmCode confirmCode = codeService.save(user);
		sendCode(confirmCode);

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getId(),
				user.getPassword(), RoleUtil.getAuthorities(user));
		SecurityContext securityContext = new SecurityContextImpl(authenticationToken);

		contextRepository.saveContext(securityContext, request, response);
	}

	private void sendCode(ConfirmCode confirmCode) {
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom(env.getProperty("auth.from"));
		message.setTo(confirmCode.getUser().getEmail());
		message.setSubject(env.getProperty("auth.subject"));
		message.setText(env.getProperty("auth.text") + " " + confirmCode.getCode());

		javaMailSender.send(message);
	}

	@Override
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws UnknownUserException {
		if (SecurityContextHolder.getContext() == null
				|| SecurityContextHolder.getContext().getAuthentication() == null) {
			throw new UnknownUserException("Context doesn't have a user");
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findById(Long.valueOf(authentication.getName()));

		refreshTokenService.removeByUser(user);
		securityContextRepository.saveContext(SecurityContextHolder.getContext(), request, response);
	}

	@Override
	public void confirmAccount(String code) throws ValidationException {
		BindingResult validationResult = BindingResultFactory.getInstance("code");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findById(Long.valueOf(auth.getName()));

		if (user.isEnabled()) {
			validationResult.reject("confirm.code.alreadyEnable", "Ваш аккаунт уже верифицирован");
		}

		ErrorParser.checkErrors(validationResult, "The user has already enabled", Type.CONFILICT);

		ConfirmCode confirmCode = codeService.findByUserId(user.getId());
		if (!confirmCode.getCode().equals(code)) {
			validationResult.reject("confirm.code.notEquals", "Код неверен");
		}

		ErrorParser.checkErrors(validationResult, "These codes are not equals", Type.BAD_REQUEST);

		user.setEnabled(true);
		userService.save(user);
		codeService.delete(confirmCode);
	}

}
