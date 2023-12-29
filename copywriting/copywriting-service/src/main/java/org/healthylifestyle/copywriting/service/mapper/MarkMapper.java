package org.healthylifestyle.copywriting.service.mapper;

import java.util.List;

import org.healthylifestyle.copywriting.common.dto.MarkDto;
import org.healthylifestyle.copywriting.model.mark.Mark;

public interface MarkMapper {
	MarkDto markToDto(Mark mark);

	List<MarkDto> marksToDtos(List<Mark> marks);
}
