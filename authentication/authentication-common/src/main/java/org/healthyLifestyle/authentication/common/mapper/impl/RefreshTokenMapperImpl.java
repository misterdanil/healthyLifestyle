package org.healthyLifestyle.authentication.common.mapper.impl;

import org.healthyLifestyle.authentication.common.dto.RefreshTokenDto;
import org.healthyLifestyle.authentication.common.mapper.RefreshTokenMapper;
import org.healthyLifestyle.authentication.model.RefreshToken;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenMapperImpl implements RefreshTokenMapper {

	@Override
	public RefreshTokenDto refreshTokenToDto(RefreshToken refreshToken) {
		RefreshTokenDto dto = new RefreshTokenDto();
		dto.setId(refreshToken.getId());
		dto.setToken(refreshToken.getToken());

		return dto;
	}

}
