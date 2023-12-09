package org.healthyLifestyle.authentication.common.mapper;

import org.healthylifestyle.user.common.dto.UserDto;
import org.healthylifestyle.user.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
	UserDto userToDto(User user);

}
