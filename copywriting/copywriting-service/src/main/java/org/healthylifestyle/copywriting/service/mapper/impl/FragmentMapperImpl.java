package org.healthylifestyle.copywriting.service.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.healthylifestyle.copywriting.common.dto.FragmentDto;
import org.healthylifestyle.copywriting.model.Fragment;
import org.healthylifestyle.copywriting.service.mapper.FragmentMapper;
import org.springframework.stereotype.Component;

@Component
public class FragmentMapperImpl implements FragmentMapper {

	@Override
	public FragmentDto fragmentToDto(Fragment fragment) {
		FragmentDto dto = new FragmentDto();
		dto.setId(fragment.getId());

		return dto;
	}

	@Override
	public List<FragmentDto> fragmentsToDtos(List<Fragment> fragments) {
		List<FragmentDto> dtos = new ArrayList<>();

		fragments.forEach(f -> dtos.add(fragmentToDto(f)));

		return dtos;
	}

}
