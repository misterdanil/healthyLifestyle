package org.healthylifestyle.communication.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.healthylifestyle.common.dto.ErrorResult;
import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.common.web.ErrorParser;
import org.healthylifestyle.communication.common.dto.AddingUserRequest;
import org.healthylifestyle.communication.common.dto.ChatCreatingRequest;
import org.healthylifestyle.communication.common.dto.ChatUpdatingRequest;
import org.healthylifestyle.communication.model.Chat;
import org.healthylifestyle.communication.model.ChatUser;
import org.healthylifestyle.communication.model.settings.Invitation;
import org.healthylifestyle.communication.model.settings.Modification;
import org.healthylifestyle.communication.model.settings.Privacy;
import org.healthylifestyle.communication.model.settings.Setting;
import org.healthylifestyle.communication.repository.ChatRepository;
import org.healthylifestyle.communication.service.ChatService;
import org.healthylifestyle.communication.service.ChatUserService;
import org.healthylifestyle.filesystem.service.ImageService;
import org.healthylifestyle.filesystem.service.dto.ImageSavingRequest;
import org.healthylifestyle.user.model.Role;
import org.healthylifestyle.user.model.User;
import org.healthylifestyle.user.repository.RoleRepository;
import org.healthylifestyle.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
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

		Role ownerRole = roleRepository.findByName("CHAT_OWNER");
		Role adminRole = roleRepository.findByName("CHAT_ADMIN");
		Role memberRole = roleRepository.findByName("CHAT_MEMBER");

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

		imageService.save(imageSavingRequest);

		chat = chatRepository.save(chat);

		return chat;
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

		List<Role> roles = chatUserService
				.findRolesByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		List<Long> candidateIds = updatingRequest.getAdminCandidateIds();
		if (candidateIds != null && !candidateIds.isEmpty()) {
			if (hasRole(roles, "CHAT_OWNER")) {
				int count = chatUserService.countChatUsersByIdIn(chat.getId(), candidateIds);
				if (count < candidateIds.size()) {
					String code = "chat.update.users.notContains";
					validationResult.rejectValue("userIds", code,
							messageSource.getMessage(code, null, LocaleContextHolder.getLocale()));

					throw new ValidationException(exceptionMessage + ". User ids aren't correct", validationResult);
				}

				Role role = roleRepository.findByName("CHAT_ADMIN");

				List<ChatUser> chatUsers = chatUserService.findAllByIds(candidateIds);
				chatUsers.forEach(cu -> cu.addRole(role));
			}
		}

		boolean isAdmin = hasRole(roles, "CHAT_ADMIN");

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
	public void joinChat(Long chatId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addUser(AddingUserRequest addingRequest) {
		// TODO Auto-generated method stub

	}

	@Override
	public void joinByInvitation(Long chatId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void leave(Long chatId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(Long chatId) {
		// TODO Auto-generated method stub

	}

}
