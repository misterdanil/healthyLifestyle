package org.healthylifestyle.communication.service.impl;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.healthylifestyle.common.dto.ErrorResult;
import org.healthylifestyle.common.error.BindingResultFactory;
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
import org.healthylifestyle.event.model.Event;
import org.healthylifestyle.event.service.EventService;
import org.healthylifestyle.filesystem.model.Image;
import org.healthylifestyle.filesystem.service.ImageService;
import org.healthylifestyle.notification.model.ChatNotification;
import org.healthylifestyle.notification.service.ChatNotificationService;
import org.healthylifestyle.user.model.Role;
import org.healthylifestyle.user.model.User;
import org.healthylifestyle.user.repository.RoleRepository;
import org.healthylifestyle.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;

import jakarta.transaction.Transactional;

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

	private static final int MAX = 50;

	private static final Logger logger = LoggerFactory.getLogger(ChatServiceImpl.class);

	@Override
	public Chat findById(Long id) throws ValidationException {
		logger.debug("Start validating request to get a chat");
		BindingResult validationResult = new MapBindingResult(new LinkedHashMap<>(), "chat");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = userService.findById(Long.valueOf(auth.getName()));
		Chat chat = chatRepository.findById(id).get();

		if (!chatUserService.isMember(chat, user)) {
			reject("chat.find.notMember", validationResult, id);

			throw new ValidationException(
					"Exception occurred while checking for a chat membership. User with id '%s' isn't a member of the chat with id '%s'",
					validationResult, user.getId(), chat.getId());
		}

		logger.debug("The request is valid");

		return chat;
	}

	@Override
	public Chat save(ChatCreatingRequest savingRequest) throws ValidationException {
		logger.debug("Start validating chat");

		BindingResult validationResult = new BeanPropertyBindingResult(savingRequest, "chatCreatingRequest");
		validator.validate(savingRequest, validationResult);

		List<Long> userIds = savingRequest.getUserIds();
		if (!validationResult.hasFieldErrors("userIds")) {
			int countUsers = userService.countAllByIdIn(userIds);

			if (countUsers < userIds.size()) {
				validationResult.rejectValue("userIds", "userIds.notContains",
						"User id list has users who doesnt exist");
			}
		}

		ErrorParser.checkErrors(validationResult, "Exception occurred while saving chat. It's not valid",
				Type.BAD_REQUEST);

		logger.debug("The chat request is valid");

		Role ownerRole = roleRepository.findByName("ROLE_CHAT_OWNER");
		Role adminRole = roleRepository.findByName("ROLE_CHAT_ADMIN");
		Role memberRole = roleRepository.findByName("ROLE_CHAT_MEMBER");

		User admin = userService
				.findById(Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName()));

		Chat chat = new Chat();
		chat.setTitle(savingRequest.getTitle());
		chat.setUuid(UUID.randomUUID().toString());

		ChatUser chatAdmin = new ChatUser();
		chatAdmin.setUser(admin);
		chatAdmin.addRoles(Arrays.asList(ownerRole, adminRole, memberRole));
		chatAdmin.setChat(chat);

		chat.addUser(chatAdmin);

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

		Image avatar = imageService.findById(savingRequest.getImageId());
		chat.setImage(avatar);

		Chat savedChat = chatRepository.save(chat);

		logger.debug("Chat has been saved");

		return savedChat;
	}

	@Override
	public List<Chat> findByTitle(String title) {
		List<Chat> chats = chatRepository.findByTitle(title);

		return chats;
	}

	@Override
	public Chat update(ChatUpdatingRequest updatingRequest, Long id) throws ValidationException {
		logger.debug("Start updating and validating update chat request");

		BindingResult validationResult = new BeanPropertyBindingResult(updatingRequest, "chatUpdatingRequest");
		validator.validate(updatingRequest, validationResult);

		String exceptionMessage = "Exception occurred while updating chat";

		ErrorParser.checkErrors(validationResult, exceptionMessage + ". The request is not valid", Type.BAD_REQUEST);

		Chat chat = chatRepository.findById(id).orElse(null);
		if (chat == null) {
			String code = "chat.update.id.notExist";
			validationResult.rejectValue("id", code,
					messageSource.getMessage(code, null, LocaleContextHolder.getLocale()));
		}

		ErrorParser.checkErrors(validationResult, exceptionMessage + ". Chat doesn't exist", Type.BAD_REQUEST);

		User user = userService
				.findById(Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName()));

		List<Role> roles = chatUserService.findRolesByChatIdAndUserId(chat.getId(), user.getId());

		logger.debug("Start checking roles");

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
			if (updatingRequest.getImageId() != null) {
				imageService.remove(chat.getImage());

				Image avatar = imageService.findById(updatingRequest.getImageId());

				chat.setImage(avatar);
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

		logger.debug("Chat has been updated");

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
		logger.debug("Start joining chat by id " + joiningRequest.getChatId());

		BindingResult validationResult = new BeanPropertyBindingResult(joiningRequest, "joininChatRequest");

		Long id = joiningRequest.getChatId();

		Optional<Chat> chatOpt = chatRepository.findById(id);
		if (!chatOpt.isPresent()) {
			rejectValue("chatId", "chat.join.id.notExist", validationResult, id);
		}

		ErrorParser.checkErrors(validationResult,
				"Exception occurred while joining chat. Couldn't get chat by id " + id, Type.NOT_FOUND);

		Chat chat = chatOpt.get();

		User user = userService
				.findById(Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName()));

		Privacy privacy = chat.getSetting().getPrivacy();

		if (privacy.equals(Privacy.CLOSED)) {
			reject("chat.join.closed", validationResult);
			ErrorParser.checkErrors(validationResult,
					"Exception occurred while saving chat. Chat with id '%s' is closed", Type.NOT_FOUND, chat.getId());
		} else if (privacy.equals(Privacy.INVITATION)) {
			joinByInvitation(chat, user, validationResult);
		} else if (privacy.equals(Privacy.OPENED)) {
			checkUserExistenceAndAdd(chat, user, validationResult, "chat.join.user.exist", user.getId());
		}

		chatRepository.save(chat);

		logger.debug("The user has been added");
	}

	private void joinByInvitation(Chat chat, User user, BindingResult validationResult) throws ValidationException {
		logger.debug("Start joining chat with id " + chat.getId() + " by invitation");

		ChatNotification invitation = chatNotificationService.findByChatIdAndToId(chat.getId(), user.getId());

		if (invitation == null) {
			reject("chat.join.invitation.notExist", validationResult);
		} else {
			checkUserExistenceAndAdd(chat, user, validationResult, "chat.join.user.exist", user.getId());

			logger.debug("The user has been added");

			chatNotificationService.delete(invitation);
		}
	}

	public void checkUserExistenceAndAdd(Chat chat, User user, BindingResult validationResult, String code,
			Object... args) throws ValidationException {
		if (!chatUserService.existsMember(chat.getId(), user.getId())) {
			ChatUser newMember = new ChatUser();
			Role role = roleRepository.findByName("ROLE_CHAT_MEMBER");
			newMember.setChat(chat);
			newMember.setUser(user);
			newMember.addRole(role);

			chat.addUser(newMember);
		} else {
			reject("chat.join.user.exist", validationResult, args);
			throw new ValidationException(
					"Exception occurred while adding user. User with id '%s' is already a member of chat with id '%s'",
					validationResult, user.getId(), chat.getId());
		}
	}

	private void rejectValue(String value, String code, BindingResult validationResult, Object... args) {
		validationResult.rejectValue(value, code, messageSource.getMessage(code, args, Locale.ENGLISH));
	}

	private void reject(String code, BindingResult validationResult, Object... args) {
		validationResult.reject(code, messageSource.getMessage(code, args, Locale.ENGLISH));
	}

	@Override
	public void inviteUser(Long chatId, Long userId) throws ValidationException {
		BindingResult validationResult = BindingResultFactory.getInstance("inviteUser");

		logger.debug("Start inviting user");

		User user = userService
				.findById(Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName()));

		Chat chat = chatRepository.findById(chatId).orElse(null);
		if (chat == null) {
			validationResult.reject("chat.invite.chat.notExist", "Couldn't get chat by id " + chatId);
		}

		User to = userService.findById(userId);
		if (to == null) {
			validationResult.reject("chat.invite.to.notExist", "Couldn't get user by id " + userId);
		}

		ErrorParser.checkErrors(validationResult, "Exception occurred while inviting user. The input data is invalid",
				Type.NOT_FOUND);

		if (user.getId().equals(to.getId())) {
			validationResult.reject("chat.invite.from.equals", "You can't send invitation to yourself");
		}

		ErrorParser.checkErrors(validationResult,
				"Exception occurred while inviting user. From and to parameters are the same", Type.BAD_REQUEST);

		Invitation invitationRule = chat.getSetting().getInvitation();
		if (invitationRule.equals(Invitation.ADMIN) && chatUserService.isAdmin(chat, user)) {
			chatNotificationService.save(chat, user, to);
		} else if (invitationRule.equals(Invitation.EVERYONE)) {
			chatNotificationService.save(chat, user, to);
		} else {
			validationResult.reject("chat.invite.from.notAdmin",
					"Вы не администратор данного чата");
		}
		ErrorParser.checkErrors(validationResult, "Exception occurred while inviting user. The user isn't an admin",
				Type.FORBIDDEN);
	}

	@Override
	public void addUser(AddingUserRequest addingRequest) throws ValidationException {
		BindingResult validationResult = new BeanPropertyBindingResult(addingRequest, "addingRequest");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User admin = userService.findById(Long.valueOf(auth.getName()));

		Long chatId = addingRequest.getChatId();
		Long userId = addingRequest.getUserId();

		Chat chat = chatRepository.findById(chatId).get();
		if (chat == null) {
			rejectValue("id", "chat.add.chatId.notExist", validationResult, chatId);
		}

		if (!chatUserService.isAdmin(chat, admin)) {
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
		logger.debug("Start joining by invitation");

		BindingResult validationResult = new BeanPropertyBindingResult(joiningRequest, "joininChatRequest");

		Long id = joiningRequest.getChatId();

		Optional<Chat> chatOpt = chatRepository.findById(id);
		if (!chatOpt.isPresent()) {
			validationResult.rejectValue("chatId", "chat.joinByInvitation.chatId.notExist",
					"There is no chat by this id");
		}

		ErrorParser.checkErrors(validationResult,
				"Exception occurred while joining chat by invitation. Couldn't get chat by id " + id, Type.BAD_REQUEST);

		logger.debug("The request is valid");

		Chat chat = chatOpt.get();

		User user = userService
				.findById(Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName()));

		joinByInvitation(chat, user, validationResult);
	}

	@Override
	@Transactional
	public void leave(LeavingChatRequest leavingRequest) throws ValidationException {
		logger.debug("Start leaving chat and validating request");

		BindingResult validationResult = getBindingResult(leavingRequest, "leavingChatRequest");

		ErrorParser.checkErrors(validationResult, "Exception occurred while leaving chat. Id must be not null",
				Type.BAD_REQUEST);

		Long chatId = leavingRequest.getChatId();

		Chat chat = chatRepository.findById(chatId).get();

		if (chat == null) {
			rejectValue("chatId", "chat.leave.chatId.notExist", validationResult, chatId);

			ErrorParser.checkErrors(validationResult,
					"Exception occurred while leaving chat. There is no chat with this id '%s'", Type.NOT_FOUND,
					chatId);
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = userService.findById(Long.valueOf(auth.getName()));

		if (!chatUserService.isMember(chat, user)) {
			reject("chat.leave.notMember", validationResult);

			throw new ValidationException(
					"Exception occurred while leaving chat. A user with id '%s' isn't member of this chat '%s'",
					validationResult, Type.FORBIDDEN, user.getId(), chatId);
		}

		logger.debug("The user is a member");

		if (chatUserService.isOwner(chat, user)) {
			RemovingChatRequest removingRequest = new RemovingChatRequest();
			removingRequest.setChatId(chatId);

			remove(removingRequest);
		} else {
			ChatUser cu = chatUserService.findByChatAndUser(chatId, user.getId());
			chat.getUsers().remove(cu);
			chatRepository.save(chat);
			chatUserService.delete(cu);

			logger.debug("The chat user has been deleted");
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

		User user = userService.findById(Long.valueOf(auth.getName()));

		if (!chatUserService.isOwner(chat, user)) {
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
		logger.debug("Start attaching event");

		BindingResult validationResult = getBindingResult(attachRequest, "attachRequest");

		ErrorParser.checkErrors(validationResult, "Exception occurred while checking id. Id can't be null",
				Type.BAD_REQUEST);

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

		ErrorParser.checkErrors(validationResult,
				"Exception occurred while checking chat id and event id for existence. They both must exist",
				Type.BAD_REQUEST);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findById(Long.valueOf(auth.getName()));

		Modification modification = chat.getSetting().getModification();
		if (modification.equals(Modification.ADMIN)) {
			if (!chatUserService.isAdmin(chat, user)) {
				reject("chat.attachEvent.notAdmin", validationResult, chatId);

				throw new ValidationException(
						"Exception occurred while checking admin opportunite to attach event. The user with id '%s' isn't an admin of chat '%s'",
						validationResult, Type.FORBIDDEN, user.getId(), chatId);
			}
		}
		chat.setEvent(event);
		logger.debug("The event has been attached to chat");

		chatRepository.save(chat);
	}

	@Override
	public Chat findByMessage(Long messageId) {
		return chatRepository.findByMessage(messageId);
	}

	@Override
	public List<Chat> findAllOwn(int page) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		List<Chat> chats = chatRepository.findByUser(Long.valueOf(auth.getName()), PageRequest.of(page - 1, MAX));

		return chats;
	}
}
