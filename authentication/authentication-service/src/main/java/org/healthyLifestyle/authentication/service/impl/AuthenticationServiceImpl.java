package org.shop.authentication.service.impl;

import org.shop.authentication.common.dto.AuthenticationRequest;
import org.shop.authentication.common.dto.SignUpRequest;
import org.shop.authentication.model.ConfirmCode;
import org.shop.authentication.model.RefreshToken;
import org.shop.authentication.service.AuthenticationService;
import org.shop.authentication.service.ConfirmCodeService;
import org.shop.authentication.service.RefreshTokenService;
import org.shop.authentication.service.error.UnknownUserException;
import org.shop.authentication.service.oauth2.repository.JwtSecurityContextRepository;
import org.shop.authentication.service.provider.impl.RefreshTokenProvider;
import org.shop.user.model.User;
import org.shop.user.service.UserService;
import org.shop.user.service.error.OAuth2UserExistException;
import org.shop.user.service.error.ValidationException;
import org.shop.user.service.util.RoleUtil;
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
	private RefreshTokenService refreshTokenService;
	@Autowired
	private RefreshTokenProvider refreshTokenProvider;
	@Autowired
	private SecurityContextRepository securityContextRepository;

	@Override
	public void login(AuthenticationRequest authenticationRequest, HttpServletRequest request,
			HttpServletResponse response) throws ValidationException {
		BindingResult bindingResult = new BeanPropertyBindingResult(authenticationRequest, "authenticationRequest");

		String email = authenticationRequest.getEmail();
		String password = authenticationRequest.getPassword();

		User user = userService.findByEmail(email);
		if (user == null) {
			bindingResult.rejectValue("email", "email.not_exists", "email not exists");
			throw new ValidationException(bindingResult);
		}

		if (!passwordEncoder.matches(password, user.getPassword())) {
			bindingResult.rejectValue("password", "password.mismatch", "password is mismatch");
			throw new ValidationException(bindingResult);
		}

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getId(),
				user.getPassword(), RoleUtil.getAuthorities(user));
		SecurityContext securityContext = new SecurityContextImpl(authenticationToken);

		contextRepository.saveContext(securityContext, request, response);
	}

	@Override
	public void signUp(SignUpRequest signUpRequest, HttpServletRequest request, HttpServletResponse response)
			throws ValidationException {
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
			HttpServletResponse response) throws OAuth2UserExistException {
		User user = userService.save(signUpRequest, resourceId, resourceName);

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getId(),
				user.getPassword(), RoleUtil.getAuthorities(user));
		SecurityContext securityContext = new SecurityContextImpl(authenticationToken);

		contextRepository.saveContext(securityContext, request, response);
	}

	private void sendCode(ConfirmCode confirmCode) {
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom(env.getProperty("auth.mail"));
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

}
