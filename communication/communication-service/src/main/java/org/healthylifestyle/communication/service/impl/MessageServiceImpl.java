package org.healthylifestyle.communication.service.impl;

import java.util.List;
import java.util.Optional;

import org.healthylifestyle.common.error.BindingResultFactory;
import org.healthylifestyle.common.error.Type;
import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.common.web.ErrorParser;
import org.healthylifestyle.communication.common.dto.message.SaveMessageRequest;
import org.healthylifestyle.communication.common.dto.message.UpdateMessageRequest;
import org.healthylifestyle.communication.model.Chat;
import org.healthylifestyle.communication.model.ChatUser;
import org.healthylifestyle.communication.model.Message;
import org.healthylifestyle.communication.repository.MessageRepository;
import org.healthylifestyle.communication.service.ChatService;
import org.healthylifestyle.communication.service.ChatUserService;
import org.healthylifestyle.communication.service.MessageService;
import org.healthylifestyle.filesystem.model.Image;
import org.healthylifestyle.filesystem.model.Video;
import org.healthylifestyle.filesystem.model.Voice;
import org.healthylifestyle.filesystem.service.ImageService;
import org.healthylifestyle.filesystem.service.VideoService;
import org.healthylifestyle.filesystem.service.VoiceService;
import org.healthylifestyle.user.model.User;
import org.healthylifestyle.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

@Service
public class MessageServiceImpl implements MessageService {
	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private ImageService imageService;
	@Autowired
	private VideoService videoService;
	@Autowired
	private VoiceService voiceService;
	@Autowired
	private ChatService chatService;
	@Autowired
	private ChatUserService chatUserService;
	@Autowired
	private UserService userService;
	@Autowired
	private Validator validator;
	@Autowired
	private MessageSource messageSource;

	private static final int MESSAGES_LIMIT = 50;

	@Override
	public Message findById(Long messageId) {
		return messageRepository.findById(messageId).orElse(null);
	}

	@Override
	public Message save(SaveMessageRequest saveRequest, Long chatId) throws ValidationException {
		BindingResult validationResult = BindingResultFactory.getInstance(saveRequest, "saveRequest", validator);

		ErrorParser.checkErrors(validationResult, "Exception occurred while checking saving data. Data is invalid",
				Type.BAD_REQUEST);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByUsername(auth.getName());

		if (!chatService.isMember(chatId, user.getId())) {
			ErrorParser.reject("message.save.notMember", validationResult, messageSource);
		}

		ErrorParser.checkErrors(validationResult,
				"Exception occurred while authorizing a user. The user isn't member of this chat '%s'", Type.FORBIDDEN,
				chatId);

		ChatUser chatUser = chatUserService.findByChatAndUser(chatId, user.getId());

		Message message = new Message();
		message.setValue(saveRequest.getText());
		message.setChatUser(chatUser);
		message.setChat(chatUser.getChat());

		List<Image> images = imageService.findAllByIdIn(saveRequest.getImageIds());
		message.addImages(images);

		List<Video> videos = videoService.findAllByIdIn(saveRequest.getVideoIds());
		message.addVideos(videos);

		List<Voice> voices = voiceService.findAllByIdIn(saveRequest.getVoiceIds());
		message.addVoices(voices);

		message = messageRepository.save(message);

		return message;
	}

	@Override
	public List<Message> findByTextIn(Long chatId, String text, int page) throws ValidationException {
		BindingResult validationResult = BindingResultFactory.getInstance("message");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByUsername(auth.getName());

		if (!chatService.isMember(chatId, user.getId())) {
			ErrorParser.reject("message.find.notMember", validationResult, messageSource);
		}

		ErrorParser.checkErrors(validationResult,
				"Exception occurred while finding messages by text in. The user '%s' isn't a member of the chat '%s'",
				Type.FORBIDDEN, user.getId(), chatId);

		List<Message> messages = messageRepository.findByTextIn(chatId, text,
				PageRequest.of(page - 1, MESSAGES_LIMIT, Sort.by("createdOn").descending()));

		return messages;
	}

	@Override
	public List<Message> findAllByChatId(Long chatId, int page) throws ValidationException {
		BindingResult validationResult = BindingResultFactory.getInstance("message");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByUsername(auth.getName());

		if (!chatService.isMember(chatId, user.getId())) {
			ErrorParser.reject("message.find.notMember", validationResult, messageSource);
		}

		ErrorParser.checkErrors(validationResult,
				"Exception occurred while finding messages. The user '%s' isn't a member of the chat '%s'",
				Type.FORBIDDEN, user.getId(), chatId);

		List<Message> messages = messageRepository.findByChat(chatId,
				PageRequest.of(page - 1, MESSAGES_LIMIT, Sort.by("createdOn").descending()));

		return messages;
	}

	@Override
	public void delete(Long messageId) throws ValidationException {
		BindingResult validationResult = BindingResultFactory.getInstance("message");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByUsername(auth.getName());

		if (!messageRepository.isOwner(messageId, user.getId())) {
			ErrorParser.reject("message.delete.notOwner", validationResult, messageSource);
		}

		ErrorParser.checkErrors(validationResult,
				"Exception occurred while deleting a message. The message '%s' doesn't belong to user '%s'",
				Type.FORBIDDEN, messageId, user.getId());

		Optional<Message> optMessage = messageRepository.findById(messageId);
		if (optMessage.isEmpty()) {
			ErrorParser.reject("message.delete.notFound", validationResult, messageSource, messageId);
		}

		ErrorParser.checkErrors(validationResult,
				"Exception occurred while deleting message. There is not message with id '%s'", Type.NOT_FOUND,
				messageId);

		Message message = optMessage.get();

		messageRepository.delete(message);
	}

	@Override
	public void update(UpdateMessageRequest updateRequest, Long id) throws ValidationException {
		BindingResult validationResult = BindingResultFactory.getInstance(updateRequest, "updateRequest", validator);
		ErrorParser.checkErrors(validationResult,
				"Exception occurred while updating message with id '%s'. Update request is invalid", Type.BAD_REQUEST);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByUsername(auth.getName());

		if (!messageRepository.isOwner(id, user.getId())) {
			ErrorParser.reject("message.update.notOwner", validationResult, messageSource);
		}
		ErrorParser.checkErrors(validationResult,
				"Exception occurred while updating message '%s'. The user '%s' is not owner of it", Type.FORBIDDEN, id,
				user.getId());

		Optional<Message> message = messageRepository.findById(id);
		if (message.isEmpty()) {
			ErrorParser.reject("message.update.notExist", validationResult, messageSource, id);
		}
		ErrorParser.checkErrors(validationResult,
				"Exception occurred while updating message '%s'. This message doesn't exist", Type.NOT_FOUND, id);

		imageService.deleteByMessage(updateRequest.getDeletedImageIds(), message.get(), user);
		videoService.deleteByMessage(updateRequest.getDeletedVideoIds(), message.get(), user);
		voiceService.deleteByMessage(updateRequest.getDeletedVideoIds(), message.get(), user);

		List<Image> images = imageService.findAllByIdIn(updateRequest.getImageIds());
		message.get().addImages(images);

		List<Video> videos = videoService.findAllByIdIn(updateRequest.getVideoIds());
		message.get().addVideos(videos);

		List<Voice> voices = voiceService.findAllByIdIn(updateRequest.getVoiceIds());
		message.get().addVoices(voices);

		messageRepository.save(message.get());
	}

	@Override
	public void addToFavorites(Long messageId) throws ValidationException {
		BindingResult validationResult = BindingResultFactory.getInstance("message");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByUsername(auth.getName());

		Optional<Message> message = messageRepository.findById(messageId);
		if (message.isEmpty()) {
			ErrorParser.reject("message.addFavorites.notFound", validationResult, messageSource);
		}
		ErrorParser.checkErrors(validationResult,
				"Exception occurred while adding a message '%s' to favorites to user '%s'. There is no chat with this id.",
				Type.BAD_REQUEST, messageId, user.getId());

		Chat chat = message.get().getChat();

		ChatUser chatUser = chatUserService.findByChatAndUser(chat.getId(), user.getId());
		if (chatUser == null) {
			ErrorParser.reject("message.addFavorites.chat.notMember", validationResult, messageSource);
		}
		ErrorParser.checkErrors(validationResult,
				"Exception occurred while adding a message '%s' to favoites to user '%s'. The user isn't a member of the chat '%s'",
				Type.FORBIDDEN, messageId, user.getId(), chat.getId());

		chatUser.addFavoriteMessage(message.get());

		chatUserService.update(chatUser);
	}

	@Override
	public Message answerMessage(SaveMessageRequest saveRequest, Long id) throws ValidationException {
		BindingResult validationResult = BindingResultFactory.getInstance("message");

		Optional<Message> answeredMessage = messageRepository.findById(id);
		if (answeredMessage.isEmpty()) {
			ErrorParser.reject("message.answer.notFound", validationResult, messageSource, id);
		}
		ErrorParser.checkErrors(validationResult,
				"Exception occurred while answering the message '%s', It doesn't exist", Type.NOT_FOUND, id);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByUsername(auth.getName());

		Chat chat = answeredMessage.get().getChat();
		if (!chatService.isMember(chat.getId(), user.getId())) {
			ErrorParser.reject("message.answer.notMember", validationResult, messageSource, chat.getId());
		}

		ErrorParser.checkErrors(validationResult,
				"Exception occurred while answering the message '%s'. The user '%s' isn't member of this chat '%s'",
				Type.FORBIDDEN, id, user.getId(), chat.getId());

		Message message = save(saveRequest, chat.getId());

		message.setAnsweredMessage(answeredMessage.get());

		message = messageRepository.save(message);

		return message;
	}

	@Override
	public List<Message> findFavorites(int page) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByUsername(auth.getName());

		List<Message> messages = messageRepository.findFavorites(user.getId(),
				PageRequest.of(page - 1, MESSAGES_LIMIT));

		return messages;
	}

}
