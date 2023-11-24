package org.healthylifestyle.filesystem.service;

import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.filesystem.model.Image;
import org.healthylifestyle.filesystem.service.dto.ImageSavingRequest;

public interface ImageService {
	Image save(ImageSavingRequest savingRequest) throws ValidationException;
	
	void remove(Image image);
}
