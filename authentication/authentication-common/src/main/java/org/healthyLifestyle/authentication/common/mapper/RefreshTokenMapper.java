package org.healthyLifestyle.authentication.common.mapper;

import org.mapstruct.Mapper;
import org.shop.authentication.common.dto.RefreshTokenDto;
import org.shop.authentication.model.RefreshToken;

@Mapper
public interface RefreshTokenMapper {
	RefreshTokenDto refreshTokenToDto(RefreshToken refreshToken);
}
