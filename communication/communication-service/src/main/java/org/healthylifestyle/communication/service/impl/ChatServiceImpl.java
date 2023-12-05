package org.healthylifestyle.communication.service.impl;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.healthylifestyle.common.dto.ErrorResult;
import org.healthylifestyle.common.error.Type;
import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.common.web.ErrorParser;
import org.healthylifestyle.communication.common.dto.AddingUserRequest;
import org.healthylifestyle.communication.common.dto.AttachEventRequest;
import org.healthylifestyle.communication.common.dto.ChatCreatingRequest;
import org.healthylifestyle.communication.common.dto.ChatUpdatingRequest;
import org.healthylifestyle.communication.common.dto.JoiningChatRequest;
import org.healthylifestyle.communication.common.dto.LeavingChatRequest;
import org.healthylifestyle.communication.common.dto.RemovingChatRequest;
import org.healthylifestyle.communication.model.Chat;
import org.healthylifestyle.communication.model.ChatUser;
import org.healthylifestyle.communication.model.settings.Invitation;
import org.healthylifestyle.communication.model.settings.Modification;
import org.healthylifestyle.communication.model.settings.Privacy;
import org.healthylifestyle.communication.model.settings.Setting;
import org.healthylifestyle.communication.repository.ChatRepository;
import org.healthylifestyle.communication.service.ChatService;
import org.healthylifestyle.communication.service.ChatUserService;
import org.healthylifestyle.filesystem.model.Image;
import org.healthylifestyle.filesystem.service.ImageService;
import org.healthylifestyle.filesystem.service.dto.ImageSavingRequest;
import org.healthylifestyle.notification.model.ChatNotification;
import org.healthylifestyle.notification.service.ChatNotificationService;
import org.healthylifestyle.user.model.Role;
import org.healthylifestyle.user.model.User;
import org.healthylifestyle.user.model.lifestyle.healthy.event.Event;
import org.healthylifestyle.user.repository.RoleRepository;
import org.healthylifestyle.user.service.EventService;
import org.healthylifestyle.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ChatServiceImpl implements ChatService {
	@Autowired
	private ChatRepository chatRepository;
	@Autowired
	private Validator validator;
	@Autowired
	private UserService userService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private ImageService imageService;
	@Autowired
	private ChatUserService chatUserService;
	@Autowired
	private ChatNotificationService chatNotificationService;
	@Autowired
	private EventService eventService;

	@Override
	public Chat findById(Long id) throws ValidationException {
		BindingResult validationResult = new MapBindingResult(new LinkedHashMap<>(), "chat");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = userService.findByUsername(auth.getName());
		Chat chat = chatRepository.findById(id).get();

		if (!isMember(chat, user)) {
			reject("chat.find.notMember", validationResult, id);

			throw new ValidationException(
					"Exception occurred while checking for a chat membership. User with id '%s' isn't a member of the chat with id '%s'",
					validationResult, user.getId(), chat.getId());
		}

		return chat;
	}

	@Override
	public Chat save(ChatCreatingRequest savingRequest, MultipartFile image) throws ValidationException {
		BindingResult validationResult = new BeanPropertyBindingResult(savingRequest, "chatCreatingRequest");
		validator.validate(savingRequest, validationResult);

		List<Long> userIds = savingRequest.getUserIds();
		if (!validationResult.hasFieldErrors("userIds")) {
			int countUsers = userService.countAllByIdIn(userIds);

			if (countUsers < userIds.size()) {
				validationResult.rejectValue("userIds", "userIds.notContains",
						messageSource.getMessage("users.notContains", null, LocaleContextHolder.getLocale()));
			}
		}

		if (validationResult.hasErrors()) {
			ErrorResult parsedResult = ErrorParser.getErrorResult(validationResult);

			throw new ValidationException("Exception occurred while saving chat. It's not valid", parsedResult);
		}

		Role ownerRole = roleRepository.findByName("ROLE_CHAT_OWNER");
		Role adminRole = roleRepository.findByName("ROLE_CHAT_ADMIN");
		Role memberRole = roleRepository.findByName("ROLE_CHAT_MEMBER");

		User admin = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		ChatUser chatAdmin = new ChatUser();
		chatAdmin.setUser(admin);
		chatAdmin.addRoles(Arrays.asList(ownerRole, adminRole, memberRole));

		Chat chat = new Chat();
		chat.setTitle(savingRequest.getTitle());
		chat.setUuid(UUID.randomUUID().toString());

		List<User> users = userService.findAllByIds(userIds);
		users.forEach(user -> {
			ChatUser chatUser = new ChatUser();
			chatUser.setUser(user);
			chatUser.addRole(memberRole);
			chatUser.setChat(chat);

			chat.addUser(chatUser);
		});

		Setting setting = new Setting();
		setting.setInvitation(savingRequest.getInvitation());
		setting.setModification(savingRequest.getModification());
		setting.setPrivacy(savingRequest.getPrivacy());

		chat.setSetting(setting);

		ImageSavingRequest imageSavingRequest = new ImageSavingRequest();
		imageSavingRequest
				.setRelativePath(messageSource.getMessage("chat.image.path", new Object[] { chat.getUuid() }, null));

		Image avatar = imageService.save(imageSavingRequest);
		chat.setImage(avatar);

		Chat savedChat = chatRepository.save(chat);

		return savedChat;
	}

	@Override
	public List<Chat> findByTitle(String title) {
		List<Chat> chats = chatRepository.findByTitle(title);

		return chats;
	}

	@Override
	public Chat update(ChatUpdatingRequest updatingRequest, MultipartFile multipartFile) throws ValidationException {
		BindingResult validationResult = new BeanPropertyBindingResult(updatingRequest, "chatUpdatingRequest");
		validator.validate(updatingRequest, validationResult);

		String exceptionMessage = "Exception occurred while updating chat";

		if (validationResult.hasErrors()) {
			throw new ValidationException(exceptionMessage + ". The request is not valid", validationResult);
		}

		Chat chat = chatRepository.findById(updatingRequest.getId()).get();
		if (chat == null) {
			String code = "chat.update.id.notExist";
			validationResult.rejectValue("id", code,
					messageSource.getMessage(code, null, LocaleContextHolder.getLocale()));

			throw new ValidationException(exceptionMessage + ". Chat doesn't exist", validationResult);
		}

		User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		List<Role> roles = chatUserService.findRolesByChatIdAndUserId(chat.getId(), user.getId());

		List<Long> candidateIds = updatingRequest.getAdminCandidateIds();
		if (candidateIds != null && !candidateIds.isEmpty()) {
			if (hasRole(roles, "ROLE_CHAT_OWNER")) {
				int count = chatUserService.countChatUsersByIdIn(chat.getId(), candidateIds);
				if (count < candidateIds.size()) {
					String code = "chat.update.users.notContains";
					validationResult.rejectValue("userIds", code,
							messageSource.getMessage(code, null, LocaleContextHolder.getLocale()));

					throw new ValidationException(exceptionMessage + ". User ids aren't correct", validationResult);
				}

				Role role = roleRepository.findByName("ROLE_CHAT_ADMIN");

				List<ChatUser> chatUsers = chatUserService.findAllByIds(candidateIds);
				chatUsers.forEach(cu -> cu.addRole(role));
			}
		}

		boolean isAdmin = hasRole(roles, "ROLE_CHAT_ADMIN");

		if (chat.getSetting().getModification().equals(Modification.EVERYONE) || isAdmin) {
			if (multipartFile != null) {
				imageService.remove(chat.getImage());

				ImageSavingRequest imageSavingRequest = new ImageSavingRequest();
				imageSavingRequest.setFile(multipartFile);
				imageSavingRequest.setRelativePath(
						messageSource.getMessage("chat.image.path", new Object[] { chat.getUuid() }, null));

				imageService.save(imageSavingRequest);
			}

			if (updatingRequest.getTitle() != null && !updatingRequest.getTitle().isEmpty()) {
				chat.setTitle(updatingRequest.getTitle());
			}
		}

		if (isAdmin) {
			Invitation invitation = updatingRequest.getInvitation();
			Modification modification = updatingRequest.getModification();
			Privacy privacy = updatingRequest.getPrivacy();

			Setting setting = chat.getSetting();

			if (invitation != null) {
				setting.setInvitation(invitation);
			}

			if (modification != null) {
				setting.setModification(modification);
			}

			if (privacy != null) {
				setting.setPrivacy(privacy);
			}
		}

		chat = chatRepository.save(chat);

		return chat;
	}

	private boolean hasRole(List<Role> roles, String name) {
		for (Role role : roles) {
			if (role.getName().equals(name)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void joinChat(JoiningChatRequest joiningRequest) throws ValidationException {
		BindingResult validationResult = new BeanPropertyBindingResult(joiningRequest, "joininChatRequest");

		Long id = joiningRequest.getChatId();

		Optional<Chat> chatOpt = chatRepository.findById(id);
		if (!chatOpt.isPresent()) {
			rejectValue("chatId", "chat.join.id.notExist", validationResult, id);
		}

		if (validationResult.hasErrors()) {
			throw new ValidationException("Exception occurred while joining chat. Couldn't get chat by id " + id,
					validationResult);
		}

		Chat chat = chatOpt.get();

		User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		Privacy privacy = chat.getSetting().getPrivacy();

		if (privacy.equals(Privacy.CLOSED)) {
			reject("chat.join.closed", validationResult);

			throw new ValidationException("Exception occurred while saving chat. Chat with id '%s' is closed",
					validationResult, chat.getId());
		} else if (privacy.equals(Privacy.INVITATION)) {
			joinByInvitation(chat, user, validationResult);
		} else if (privacy.equals(Privacy.OPENED)) {
			checkUserExistenceAndAdd(chat, user, validationResult, "chat.join.user.exist", user.getId());
		}

		chatRepository.save(chat);
	}

	private void joinByInvitation(Chat chat, User user, BindingResult validationResult) throws ValidationException {
		ChatNotification invitation = chatNotificationService.findByChatIdAndToId(chat.getId(), user.getId());

		if (invitation == null) {
			reject("chat.join.invitation.notExist", validationResult);
		} else {
			checkUserExistenceAndAdd(chat, user, validationResult, "chat.join.user.exist", user.getId());
		}
	}

	public void checkUserExistenceAndAdd(Chat chat, User user, BindingResult validationResult, String code,
			Object... args) throws ValidationException {
		if (!chatUserService.existsMember(chat.getId(), user.getId())) {
			ChatUser newMember = new ChatUser();
			newMember.setChat(chat);
			newMember.setUser(user);

			chat.addUser(newMember);
		} else {
			reject("chat.join.user.exist", validationResult, args);
			throw new ValidationException(
					"Exception occurred while adding user. User with id '%s' is already a member of chat with id '%s'",
					validationResult, user.getId(), chat.getId());
		}
	}

	private void rejectValue(String value, String code, BindingResult validationResult, Object... args) {
		validationResult.rejectValue(value, code,
				messageSource.getMessage(code, args, LocaleContextHolder.getLocale()));
	}

	private void reject(String code, BindingResult validationResult, Object... args) {
		validationResult.reject(code, messageSource.getMessage(code, args, LocaleContextHolder.getLocale()));
	}

	@Override
	public void addUser(AddingUserRequest addingRequest) throws ValidationException {
		BindingResult validationResult = new BeanPropertyBindingResult(addingRequest, "addingRequest");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User admin = userService.findByUsername(auth.getName());

		Long chatId = addingRequest.getChatId();
		Long userId = addingRequest.getUserId();

		Chat chat = chatRepository.findById(chatId).get();
		if (chat == null) {
			rejectValue("id", "chat.add.chatId.notExist", validationResult, chatId);
		}

		if (!isAdmin(chat, admin)) {
			reject("chat.add.notAdmin", validationResult);

			throw new ValidationException("Exception occurred while adding user. You don't have this right",
					validationResult, Type.FORBIDDEN);
		}

		User user = userService.findById(userId);
		if (user == null) {
			rejectValue("id", "chat.add.userId.notExist", validationResult, userId);
		}

		if (validationResult.hasErrors()) {
			throw new ValidationException("Exception occurred while adding user. The request has missing data",
					validationResult);
		}

		checkUserExistenceAndAdd(chat, user, validationResult, "chat.add.user.exist", user.getId());

		chatRepository.save(chat);
	}

	@Override
	public void joinByInvitation(JoiningChatRequest joiningRequest) throws ValidationException {
		BindingResult validationResult = new BeanPropertyBindingResult(joiningRequest, "joininChatRequest");

		Long id = joiningRequest.getChatId();

		Optional<Chat> chatOpt = chatRepository.findById(id);
		if (!chatOpt.isPresent()) {
			rejectValue("chatId", "chat.join.id.notExist", validationResult, id);
		}

		if (validationResult.hasErrors()) {
			throw new ValidationException(
					"Exception occurred while joining chat by invitation. Couldn't get chat by id " + id,
					validationResult);
		}

		Chat chat = chatOpt.get();

		User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		joinByInvitation(chat, user, validationResult);
	}

	@Override
	public void leave(LeavingChatRequest leavingRequest) throws ValidationException {
		BindingResult validationResult = getBindingResult(leavingRequest, "leavingChatRequest");

		checkErrors(validationResult, "Exception occurred while leaving chat. Id must be not null", Type.BAD_REQUEST);

		Long chatId = leavingRequest.getChatId();

		Chat chat = chatRepository.findById(chatId).get();

		if (chat == null) {
			rejectValue("chatId", "chat.leave.chatId.notExist", validationResult, chatId);

			throw new ValidationException("Exception occurred while leaving chat. There is no chat with this id '%s'",
					validationResult, Type.BAD_REQUEST, chatId);
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = userService.findByUsername(auth.getName());

		if (!isMember(chat, user)) {
			reject("chat.leave.notMember", validationResult);

			throw new ValidationException(
					"Exception occurred while leaving chat. A user with id '%s' isn't member of this chat '%s'",
					validationResult, Type.FORBIDDEN, user.getId(), chatId);
		}

		if (isOwner(chat, user)) {
			RemovingChatRequest removingRequest = new RemovingChatRequest();
			removingRequest.setChatId(chatId);

			remove(removingRequest);
		} else {
			chatUserService.deleteByUserAndChat(user, chat);
		}

	}

	private <T> BindingResult getBindingResult(T target, String name) {
		BindingResult validationResult = new BeanPropertyBindingResult(target, name);
		validator.validate(target, validationResult);

		return validationResult;
	}

	@Override
	public void remove(RemovingChatRequest removingRequest) throws ValidationException {
		BindingResult validationResult = new BeanPropertyBindingResult(removingRequest, "removingChatRequest");
		validator.validate(removingRequest, validationResult);

		checkErrors(validationResult, "Exception occurred while removing chat. Id must be not null", Type.BAD_REQUEST);

		Long chatId = removingRequest.getChatId();

		Chat chat = chatRepository.findById(chatId).get();
		if (chat == null) {
			rejectValue("chatId", "chat.remove.chatId", validationResult, chatId);

			throw new ValidationException("Exception occurred while removing chat. There is no chat with this id '%s'",
					validationResult, Type.BAD_REQUEST, chatId);
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = userService.findByUsername(auth.getName());

		if (!isOwner(chat, user)) {
			reject("chat.remove.notOwner", validationResult);

			throw new ValidationException("Exception occurred while removing chat. A user isn't an owner",
					validationResult, Type.FORBIDDEN);
		}

		chatRepository.delete(chat);
	}

	private void checkErrors(BindingResult validationResult, String message, Type type, Object... args)
			throws ValidationException {
		if (validationResult.hasErrors()) {
			throw new ValidationException(message, validationResult, type, args);
		}
	}

	@Override
	public void attachEvent(AttachEventRequest attachRequest) throws ValidationException {
		BindingResult validationResult = getBindingResult(attachRequest, "attachRequest");

		checkErrors(validationResult, "Exception occurred while checking id. Id can't be null", Type.BAD_REQUEST);

		Long eventId = attachRequest.getEventId();
		Long chatId = attachRequest.getChatId();

		Chat chat = chatRepository.findById(chatId).get();
		if (chat == null) {
			rejectValue("chatId", "chat.attachEvent.chatId.notExist", validationResult, chatId);
		}

		Event event = eventService.findById(eventId);
		if (event == null) {
			rejectValue("chatId", "chat.attachEvent.eventId.notExist", validationResult, eventId);
		}

		checkErrors(validationResult,
				"Exception occurred while checking chat id and event id for existence. They both must exist",
				Type.BAD_REQUEST);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByUsername(auth.getName());

		Modification modification = chat.getSetting().getModification();
		if (modification.equals(Modification.ADMIN)) {
			if (!isAdmin(chat, user)) {
				reject("chat.attachEvent.notAdmin", validationResult, chatId);

				throw new ValidationException(
						"Exception occurred while checking admin opportunite to attach event. The user with id '%s' isn't an admin of chat '%s'",
						validationResult, Type.FORBIDDEN, user.getId(), chatId);
			}
		} else {
			chat.setEvent(event);

			chatRepository.save(chat);
		}
	}

	@Override
	public boolean isMember(Chat chat, User user) {
		return chatRepository.isMember(chat, user);
	}

	@Override
	public boolean isMember(Long chatId, Long userId) {
		return chatRepository.isMember(chatId, userId);
	}

	@Override
	public boolean isAdmin(Chat chat, User user) {
		return chatRepository.isAdmin(chat, user);
	}

	@Override
	public boolean isOwner(Chat chat, User user) {
		return chatRepository.isOwner(chat, user);
	}

	@Override
	public Chat findByMessage(Long messageId) {
		return chatRepository.findByMessage(messageId);
	}
	
	

}
