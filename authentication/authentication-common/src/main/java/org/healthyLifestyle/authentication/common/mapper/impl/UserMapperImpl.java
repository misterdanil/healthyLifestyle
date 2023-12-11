package org.healthyLifestyle.authentication.common.mapper.impl;

import org.healthyLifestyle.authentication.common.mapper.UserMapper;
import org.healthylifestyle.user.common.dto.UserDto;
import org.healthylifestyle.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

	@Override
	public UserDto userToDto(User user) {
		UserDto dto = new UserDto();
		dto.setId(user.getId());
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		dto.setEmail(user.getEmail());
		dto.setUsername(user.getUsername());

		return null;
	}

}
