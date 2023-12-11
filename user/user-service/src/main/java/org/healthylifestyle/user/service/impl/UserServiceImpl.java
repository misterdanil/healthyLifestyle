package org.healthylifestyle.user.service.impl;

import java.util.Arrays;
import java.util.List;

import org.healthyLifestyle.authentication.common.dto.SignUpRequest;
import org.healthylifestyle.common.error.Type;
import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.user.model.Role;
import org.healthylifestyle.user.model.User;
import org.healthylifestyle.user.repository.UserRepository;
import org.healthylifestyle.user.service.RoleService;
import org.healthylifestyle.user.service.UserService;
import org.healthylifestyle.user.service.error.OAuth2UserExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import jakarta.annotation.PostConstruct;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleService roleService;
	@Autowired
	private Environment env;

	@Override
	public User findById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public User save(SignUpRequest signUpRequest) throws ValidationException {
		BindingResult bindingResult = new BeanPropertyBindingResult(signUpRequest, "user");

		if (existsByEmail(signUpRequest.getEmail())) {
			bindingResult.rejectValue("email", "email.exist", "error message");
		}

		if (bindingResult.hasErrors()) {
			ValidationException validationException = new ValidationException(
					"Exception occurred while saving user. The user with this email '%s' already exists", bindingResult,
					Type.BAD_REQUEST, signUpRequest.getEmail());

			throw validationException;
		}

		Role role = roleService.findByName("ROLE_USER");

		User user = createUser(signUpRequest, Arrays.asList(role));

		user = userRepository.save(user);

		return user;
	}

	@Override
	public User save(SignUpRequest signUpRequest, String resourceId, String resourceName)
			throws OAuth2UserExistException {
		if (existsByResourceIdAndResourceName(resourceId, resourceName)) {
			throw new OAuth2UserExistException(String.format(
					"User with resource id '%s' and resource name '%s' is already exists", resourceId, resourceName));
		}

		Role userRole = roleService.findByName("ROLE_USER");
		Role oauth2UserRole = roleService.findByName("ROLE_OAUTH2_USER");

		User user = createUser(signUpRequest, Arrays.asList(userRole, oauth2UserRole));
		user.setResourceId(resourceId);
		user.setResourceName(resourceName);
		
		user = userRepository.save(user);

		return user;
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	private User createUser(SignUpRequest signUpRequest, List<Role> roles) {
		User user = new User();
		user.setFirstName(signUpRequest.getFirstName());
		user.setLastName(signUpRequest.getLastName());
		user.setUsername(signUpRequest.getUsername());
		user.setBirthDate(signUpRequest.getBirthDate());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(signUpRequest.getPassword());
		user.addRoles(roles);

		return user;
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public int countAllByIdIn(List<Long> ids) {
		return userRepository.countAllByIdIn(ids);
	}

	@Override
	public List<User> findAllByIds(List<Long> ids) {
		return userRepository.findAllByIds(ids);
	}

	@Override
	public User findByOAuth2IdAndOAuth2Resource(String name, String registrationId) {
		return null;
	}

	@Override
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public boolean existsByResourceIdAndResourceName(String resourceId, String resourceName) {
		return userRepository.existsByResourceIdAndResourceName(resourceId, resourceName);
	}

}
