package org.healthylifestyle.filesystem.service.impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

@Service
@PropertySource("classpath:absolute_path.properties")
public class ImageServiceImpl implements ImageService {
	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private FileService fileService;
	@Autowired
	private MessageSource messageSource;

	@Value("chat.message.image.path")
	private String chatPath;
	@Value("chat.message.image.path")
	private String messagePath;
	@Value("article.fragment.image.path")
	private String articleFragmentPath;

	@Override
	public Image findById(Long id) {
		return imageRepository.findById(id).orElse(null);
	}

	private Image save(ImageSavingRequest savingRequest, String path) throws ValidationException {
		BindingResult validationResult = new BeanPropertyBindingResult(savingRequest, "imageSavingRequest");

		MultipartFile multipartFile = savingRequest.getFile();
		String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
		String code = "image.mimeType.notSupported";
		if (!extension.equals("jpeg") && !extension.equals("jpg") && !extension.equals("png")) {
			validationResult.rejectValue("image", code,
					messageSource.getMessage(code, null, LocaleContextHolder.getLocale()));
		}

		if (validationResult.hasErrors()) {
			ErrorResult result = ErrorParser.getErrorResult(validationResult);

			throw new ValidationException("Exception occurred while saving image. The image isn't valid", result);
		}

		File file = fileService.save(multipartFile, path);

		Image image = new Image();
		image.setFile(file);

		image = imageRepository.save(image);

		return image;
	}

	@Override
	public Image saveArticleFragmentImage(ImageSavingRequest savingRequest, String articleUuid, String fragmentUuid)
			throws ValidationException {
		String path = MessageFormat.format(articleFragmentPath, articleUuid, fragmentUuid);

		Image image = save(savingRequest, path);

		return image;
	}

	@Override
	public Image saveChatImage(ImageSavingRequest savingRequest, String chatUuid) throws ValidationException {
		String path = MessageFormat.format(chatPath, chatUuid);

		Image image = save(savingRequest, path);

		return image;
	}

	@Override
	public Image saveMessageImage(ImageSavingRequest savingRequest, String chatUuid, String userUuid,
			String messageUuid) throws ValidationException {
		String path = MessageFormat.format(messagePath, chatUuid, userUuid, messageUuid);

		Image image = save(savingRequest, path);

		return image;
	}

	@Override
	public void remove(Image image) {
		fileService.remove(image.getFile());

		imageRepository.delete(image);
	}

	@Override
	public void deleteByMessage(List<Long> ids, Message message, User user) {
		imageRepository.deleteByMessage(ids, message, user);
	}

	@Override
	public List<Image> findAllByIdIn(List<Long> ids) {
		return imageRepository.findAllById(ids);
	}

}
