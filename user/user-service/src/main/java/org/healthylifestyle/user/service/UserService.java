package org.healthylifestyle.user.service;

import java.util.List;

import org.healthyLifestyle.authentication.common.dto.SignUpRequest;
import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.user.model.User;
import org.healthylifestyle.user.service.error.OAuth2UserExistException;

public interface UserService {
	User findById(Long id);
	
	User findByEmail(String email);

	User findByUsername(String username);

	int countAllByIdIn(List<Long> ids);

	List<User> findAllByIds(List<Long> ids);

	User findByOAuth2IdAndOAuth2Resource(String name, String registrationId);

	User save(SignUpRequest signUpRequest, String resourceId, String resourceName) throws OAuth2UserExistException;

	boolean existsByEmail(String email);

	boolean existsByResourceIdAndResourceName(String resourceId, String resourceName);

	User save(SignUpRequest signUpRequest) throws ValidationException;
}
