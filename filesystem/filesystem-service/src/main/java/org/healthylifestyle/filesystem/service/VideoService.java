package org.healthylifestyle.filesystem.service;

import java.util.List;

import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.communication.model.Message;
import org.healthylifestyle.filesystem.model.Video;
import org.healthylifestyle.filesystem.service.dto.VideoSavingRequest;
import org.healthylifestyle.user.model.User;

public interface VideoService {
	List<Video> findAllByIdIn(List<Long> ids);

	void deleteByMessage(List<Long> ids, Message message, User user);

	Video saveMessageVideo(VideoSavingRequest saveRequest, String chatUuid, String userUuid, String messageUuid)
			throws ValidationException;

	Video saveArticleFragmentVideo(VideoSavingRequest saveRequest, String articleUuid, String fragmentUuid)
			throws ValidationException;
}
