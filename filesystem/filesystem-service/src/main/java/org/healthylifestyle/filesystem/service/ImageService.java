package org.healthylifestyle.filesystem.service;

import java.util.List;

import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.communication.model.Message;
import org.healthylifestyle.filesystem.model.Image;
import org.healthylifestyle.filesystem.service.dto.ImageSavingRequest;
import org.healthylifestyle.user.model.User;

public interface ImageService {
	Image findById(Long id);

	List<Image> findAllByIdIn(List<Long> ids);

	Image saveArticleFragmentImage(ImageSavingRequest savingRequest, String articleUuid, String fragmentUuid)
			throws ValidationException;

	Image saveChatImage(ImageSavingRequest savingRequest, String chatUuid) throws ValidationException;

	Image saveMessageImage(ImageSavingRequest savingRequest, String chatUuid, String userUuid, String messageUuid)
			throws ValidationException;

	void remove(Image image);

	void deleteByMessage(List<Long> ids, Message message, User user);
}
