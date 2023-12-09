package org.healthyLifestyle.authentication.common.mapper;

import org.healthyLifestyle.authentication.common.dto.RefreshTokenDto;
import org.healthyLifestyle.authentication.model.RefreshToken;
import org.mapstruct.Mapper;

@Mapper
public interface RefreshTokenMapper {
	RefreshTokenDto refreshTokenToDto(RefreshToken refreshToken);
}
