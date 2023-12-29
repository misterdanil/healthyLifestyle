package org.healthylifestyle.filesystem.service.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.healthylifestyle.filesystem.common.dto.ImageDto;
import org.healthylifestyle.filesystem.model.Image;
import org.healthylifestyle.filesystem.service.mapper.FileMapper;
import org.healthylifestyle.filesystem.service.mapper.ImageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImageMapperImpl implements ImageMapper {
	@Autowired
	private FileMapper fileMapper;

	@Override
	public ImageDto imageToDto(Image image) {
		ImageDto dto = new ImageDto();
		dto.setId(image.getId());
		dto.setFile(fileMapper.fileToDto(image.getFile()));

		return dto;
	}

	@Override
	public List<ImageDto> imagesToDtos(List<Image> images) {
		List<ImageDto> dtos = new ArrayList<>();
		images.forEach(i -> dtos.add(imageToDto(i)));

		return dtos;
	}

}
