package org.healthylifestyle.filesystem.service.impl;

import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.healthylifestyle.common.dto.ErrorResult;
import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.common.web.ErrorParser;
import org.healthylifestyle.communication.model.Message;
import org.healthylifestyle.filesystem.model.File;
import org.healthylifestyle.filesystem.model.Image;
import org.healthylifestyle.filesystem.repository.ImageRepository;
import org.healthylifestyle.filesystem.service.FileService;
import org.healthylifestyle.filesystem.service.ImageService;
import org.healthylifestyle.filesystem.service.dto.ImageSavingRequest;
import org.healthylifestyle.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import jakarta.annotation.Resource;

@Service
@PropertySource("classpath:absolute_path.properties")
public class ImageServiceImpl implements ImageService {
	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private FileService fileService;
	@Autowired
	private MessageSource messageSource;
	@Resource
	private Environment env;

	private String chatPath;
	private String messagePath;
	private String articlePath;
	private String articleFragmentPath;

	private static final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

	@PostConstruct
	public void init() {
		chatPath = env.getProperty("chat.image.path");
		messagePath = env.getProperty("chat.message.image.path");
		articlePath = env.getProperty("article.image.path");
		articleFragmentPath = env.getProperty("article.fragment.image.path");
	}

	@Override
	public Image findById(Long id) {
		return imageRepository.findById(id).orElse(null);
	}

	private Image save(ImageSavingRequest savingRequest, String path) throws ValidationException {
		logger.debug("Start validating image");
		BindingResult validationResult = new BeanPropertyBindingResult(savingRequest, "imageSavingRequest");

		MultipartFile multipartFile = savingRequest.getFile();
		String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
		String code = "image.mimeType.notSupported";
		if (!extension.equals("jpeg") && !extension.equals("jpg") && !extension.equals("png")) {
			validationResult.rejectValue("file", code, "Файл должен иметь расширение jpeg, jpg или png");
		}

		if (validationResult.hasErrors()) {
			ErrorResult result = ErrorParser.getErrorResult(validationResult);

			throw new ValidationException("Exception occurred while saving image. The image isn't valid", result);
		}

		logger.debug("Image is valid");

		File file = fileService.save(multipartFile, path);

		Image image = new Image();
		image.setFile(file);

		image = imageRepository.save(image);

		logger.debug("Image has saved");

		return image;
	}

	@Override
	public Image saveArticleImage(ImageSavingRequest savingRequest, String articleUuid) throws ValidationException {
		logger.debug("Start saving article image");

		String path = MessageFormat.format(articlePath, articleUuid);

		Image image = save(savingRequest, path);

		logger.debug("Article image has saved");

		return image;
	}

	@Override
	public Image saveArticleFragmentImage(ImageSavingRequest savingRequest, String articleUuid, String fragmentUuid)
			throws ValidationException {
		logger.debug("Start saving article fragment image");

		String path = MessageFormat.format(articleFragmentPath, articleUuid, fragmentUuid);

		Image image = save(savingRequest, path);

		logger.debug("Article fragment image has saved");

		return image;
	}

	@Override
	public Image saveChatImage(ImageSavingRequest savingRequest, String chatUuid) throws ValidationException {
		logger.debug("Start saving chage image");

		String path = MessageFormat.format(chatPath, chatUuid);

		Image image = save(savingRequest, path);

		logger.debug("Chat image has saved");

		return image;
	}

	@Override
	public Image saveMessageImage(ImageSavingRequest savingRequest, String chatUuid, String messageUuid)
			throws ValidationException {
		logger.debug("Start saving message image");

		String path = MessageFormat.format(messagePath, chatUuid, messageUuid);

		Image image = save(savingRequest, path);

		logger.debug("Message image has saved");

		return image;
	}

	@Override
	public void remove(Image image) {
		logger.debug("Start deleteing image");
		imageRepository.delete(image);

		fileService.remove(image.getFile());

		logger.debug("Image has been deleted");
	}

	@Override
	public void deleteByMessage(List<Long> ids, Message message, User user) {
		logger.debug("Start deleting message");
		imageRepository.deleteByMessage(ids, message, user);
		logger.debug("Message has been deleted");
	}

	@Override
	public List<Image> findAllByIdIn(List<Long> ids) {
		return imageRepository.findAllById(ids);
	}

	@Override
	public List<Image> findByMessage(List<Long> ids, Message message, User user) {
		return imageRepository.findByMessage(ids, message, user);
	}

	@Override
	public List<Image> findByMessage(Long id) {
		return imageRepository.findByMessage(id);
	}

	@Override
	public List<Image> findByFragment(Long id) {
		return imageRepository.findByFragment(id);
	}

	@Override
	public Image findByArticle(Long id) {
		Image image = imageRepository.findByArticle(id);

		File file = image.getFile();
		file.setRelativePath(Paths.get(articlePath, file.getRelativePath()).toString());

		return image;
	}

}
