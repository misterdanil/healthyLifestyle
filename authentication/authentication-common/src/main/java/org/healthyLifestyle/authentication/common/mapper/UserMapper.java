package org.healthyLifestyle.authentication.common.mapper;

import org.mapstruct.Mapper;
import org.shop.user.common.dto.UserDto;
import org.shop.user.model.User;

@Mapper
public interface UserMapper {
	UserDto userToDto(User user);

}
