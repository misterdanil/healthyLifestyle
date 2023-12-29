package org.healthylifestyle.copywriting.service.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.healthylifestyle.copywriting.common.dto.MarkDto;
import org.healthylifestyle.copywriting.model.mark.Mark;
import org.healthylifestyle.copywriting.service.mapper.ArticleMapper;
import org.healthylifestyle.copywriting.service.mapper.MarkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MarkMapperImpl implements MarkMapper {
	@Autowired
	private ArticleMapper articleMapper;

	@Override
	public MarkDto markToDto(Mark mark) {
		MarkDto dto = new MarkDto();
		dto.setId(mark.getId());
		dto.setType(mark.getType().toString());
		dto.setArticle(articleMapper.articleToDto(mark.getArticle()));

		return dto;
	}

	@Override
	public List<MarkDto> marksToDtos(List<Mark> marks) {
		List<MarkDto> dtos = new ArrayList<>();

		marks.forEach(mark -> dtos.add(markToDto(mark)));

		return dtos;
	}

}
