package org.healthylifestyle.user.service.mapper.impl;

import org.healthylifestyle.user.common.dto.MeasureDto;
import org.healthylifestyle.user.model.lifestyle.healthy.measure.Measure;
import org.healthylifestyle.user.service.mapper.MeasureMapper;
import org.springframework.stereotype.Component;

@Component
public class MeasureMapperImpl implements MeasureMapper {

	@Override
	public MeasureDto measureToDto(Measure measure) {
		MeasureDto dto = new MeasureDto();
		dto.setType(measure.getType().toString());
		dto.setUnit(measure.getUnit().toString());

		return dto;
	}

}
