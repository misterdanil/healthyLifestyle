package org.healthylifestyle.filesystem.service;

import java.util.List;

import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.communication.model.Message;
import org.healthylifestyle.filesystem.model.Image;
import org.healthylifestyle.filesystem.service.dto.ImageSavingRequest;
import org.healthylifestyle.user.model.User;

public interface ImageService {
	List<Image> findAllByIdIn(List<Long> ids);

	Image save(ImageSavingRequest savingRequest) throws ValidationException;

	void remove(Image image);
	
	void deleteByMessage(List<Long> ids, Message message, User user);
}
