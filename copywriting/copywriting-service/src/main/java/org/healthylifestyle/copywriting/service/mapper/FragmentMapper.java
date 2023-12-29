package org.healthylifestyle.copywriting.service.mapper;

import java.util.List;

import org.healthylifestyle.copywriting.common.dto.FragmentDto;
import org.healthylifestyle.copywriting.model.Fragment;

public interface FragmentMapper {
	FragmentDto fragmentToDto(Fragment fragment);
	
	List<FragmentDto> fragmentsToDtos(List<Fragment> fragments);
}
