package org.healthyLifestyle.authentication.common.mapper;

import org.healthyLifestyle.authentication.common.dto.RefreshTokenDto;
import org.healthyLifestyle.authentication.model.RefreshToken;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface RefreshTokenMapper {
	RefreshTokenDto refreshTokenToDto(RefreshToken refreshToken);
}
