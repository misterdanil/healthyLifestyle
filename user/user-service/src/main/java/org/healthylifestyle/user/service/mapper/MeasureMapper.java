package org.healthylifestyle.user.service.mapper;

import org.healthylifestyle.user.common.dto.MeasureDto;
import org.healthylifestyle.user.model.lifestyle.healthy.measure.Measure;

public interface MeasureMapper {
	MeasureDto measureToDto(Measure measure);
}
