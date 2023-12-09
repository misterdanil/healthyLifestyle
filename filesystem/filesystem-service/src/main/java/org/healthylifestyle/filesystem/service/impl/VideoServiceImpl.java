package org.healthylifestyle.filesystem.service.impl;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.healthylifestyle.common.dto.ErrorResult;
import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.common.web.ErrorParser;
import org.healthylifestyle.communication.model.Message;
import org.healthylifestyle.filesystem.model.File;
import org.healthylifestyle.filesystem.model.Video;
import org.healthylifestyle.filesystem.repository.VideoRepository;
import org.healthylifestyle.filesystem.service.FileService;
import org.healthylifestyle.filesystem.service.VideoService;
import org.healthylifestyle.filesystem.service.dto.VideoSavingRequest;
import org.healthylifestyle.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;

@Service
@PropertySource("classpath: absolute_path.properties")
public class VideoServiceImpl implements VideoService {
	@Autowired
	private VideoRepository videoRepository;
	@Autowired
	private FileService fileService;
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private Environment env;
	private String messageVideoPath;
	private String articleFragmentVideoPath;

	@PostConstruct
	public void init() {
		messageVideoPath = env.getProperty("chat.message.image.path");
		articleFragmentVideoPath = env.getProperty("article.fragment.video.path");
	}

	@Override
	public List<Video> findAllByIdIn(List<Long> ids) {
		return videoRepository.findAllByIdIn(ids);
	}

	private Video save(VideoSavingRequest saveRequest, String path) throws ValidationException {
		BindingResult validationResult = new BeanPropertyBindingResult(saveRequest, "videoSavingRequest");

		MultipartFile multipartFile = saveRequest.getFile();
		String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
		String code = "image.mimeType.notSupported";
		if (!extension.equals("mp4") && !extension.equals("mov")) {
			validationResult.rejectValue("video", code,
					messageSource.getMessage(code, null, LocaleContextHolder.getLocale()));
		}

		if (validationResult.hasErrors()) {
			ErrorResult result = ErrorParser.getErrorResult(validationResult);

			throw new ValidationException("Exception occurred while saving video. The video isn't valid", result);
		}

		File file = fileService.save(multipartFile, path);

		Video video = new Video();
		video.setFile(file);

		video = videoRepository.save(video);

		return video;
	}

	@Override
	public Video saveMessageVideo(VideoSavingRequest saveRequest, String chatUuid, String userUuid,
			String messageUuid) throws ValidationException {
		String path = MessageFormat.format(messageVideoPath, chatUuid, userUuid, messageUuid);

		Video video = save(saveRequest, path);

		return video;
	}

	@Override
	public Video saveArticleFragmentVideo(VideoSavingRequest saveRequest, String articleUuid, String fragmentUuid)
			throws ValidationException {
		String path = MessageFormat.format(articleFragmentVideoPath, articleUuid, fragmentUuid);

		Video video = save(saveRequest, path);

		return video;
	}

	@Override
	public void deleteByMessage(List<Long> ids, Message message, User user) {
		videoRepository.deleteByMessage(ids, message, user);
	}
}
