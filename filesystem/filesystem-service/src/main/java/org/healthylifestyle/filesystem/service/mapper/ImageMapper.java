package org.healthylifestyle.filesystem.service.mapper;

import java.util.List;

import org.healthylifestyle.filesystem.common.dto.ImageDto;
import org.healthylifestyle.filesystem.model.Image;

public interface ImageMapper {
	ImageDto imageToDto(Image image);

	List<ImageDto> imagesToDtos(List<Image> images);
}
