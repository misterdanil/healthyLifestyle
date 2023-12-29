package org.healthylifestyle.filesystem.service.mapper.impl;

import org.healthylifestyle.filesystem.common.dto.FileDto;
import org.healthylifestyle.filesystem.model.File;
import org.healthylifestyle.filesystem.service.mapper.FileMapper;
import org.springframework.stereotype.Component;

@Component
public class FileMapperImpl implements FileMapper {

	@Override
	public FileDto fileToDto(File file) {
		FileDto dto = new FileDto();
		dto.setId(file.getId());
		dto.setType(file.getType());
		dto.setMimeType(file.getMimeType());
		dto.setRelativePath(file.getRelativePath());

		return dto;
	}

}
