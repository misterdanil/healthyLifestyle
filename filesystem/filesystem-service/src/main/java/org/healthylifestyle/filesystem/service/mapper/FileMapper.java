package org.healthylifestyle.filesystem.service.mapper;

import org.healthylifestyle.filesystem.common.dto.FileDto;
import org.healthylifestyle.filesystem.model.File;

public interface FileMapper {
	FileDto fileToDto(File file);
}
