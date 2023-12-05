package org.healthylifestyle.communication.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.healthylifestyle.common.error.Type;
import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.common.web.ErrorParser;
import org.healthylifestyle.communication.model.Chat;
import org.healthylifestyle.communication.model.ChatUser;
import org.healthylifestyle.communication.repository.ChatUserRepository;
import org.healthylifestyle.communication.service.ChatService;
import org.healthylifestyle.communication.service.ChatUserService;
import org.healthylifestyle.user.model.Role;
import org.healthylifestyle.user.model.User;
import org.healthylifestyle.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@Service
public class ChatUserServiceImpl implements ChatUserService {
	@Autowired
	private ChatUserRepository chatUserRepository;
	@Autowired
	private ChatService chatService;
	@Autowired
	private UserService userService;
	@Autowired
	private MessageSource messageSource;

	@Override
	public ChatUser findByChatAndUser(Long chatId, Long userId) {
		return chatUserRepository.findByChatAndUser(chatId, userId);
	}

	@Override
	public List<Role> findRolesByChatIdAndUserId(Long chatId, Long userId) {
		return chatUserRepository.findRolesByChatIdAndUserId(chatId, userId);
	}

	@Override
	public int countChatUsersByIdIn(Long chatId, List<Long> ids) {
		return chatUserRepository.countChatUsersByIdIn(chatId, ids);
	}

	@Override
	public int count(Long id) throws ValidationException {
		BindingResult validationResult = new MapBindingResult(new LinkedHashMap<>(), "chat");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByUsername(auth.getName());

		if (!chatService.isMember(id, user.getId())) {
			ErrorParser.reject("chatUser.count.notMember", validationResult, messageSource, id);

			throw new ValidationException(
					"Exception occurred while checking user membership '%s' to chat '%s'. The user isn't a member",
					validationResult, user.getId(), id);
		}

		int count = chatUserRepository.count(id);

		return count;
	}

	@Override
	public boolean existsMember(Long chatId, Long userId) {
		return chatUserRepository.existsMember(chatId, userId);
	}

	@Override
	public List<ChatUser> findAllByIds(List<Long> ids) {
		return chatUserRepository.findAllByIds(ids);
	}

	@Override
	public List<ChatUser> findByChat(Chat chat) {
		return chatUserRepository.findByChat(chat);
	}

	@Override
	public void deleteByUserAndChat(User user, Chat chat) {
		chatUserRepository.deleteByUserAndChat(user, chat);
	}

	@Override
	public List<ChatUser> findAllChatUsersByChatId(Long chatId) throws ValidationException {
		BindingResult validationResult = new MapBindingResult(new LinkedHashMap<>(), "chat");

		Chat chat = chatService.findById(chatId);
		if (chat == null) {
			ErrorParser.reject("chat.findUsers.chatId.notExist", validationResult, messageSource, chatId);

			throw new ValidationException(
					"Exception occurred while finding chat to get users. There is no chat with this id '%s'",
					validationResult, Type.BAD_REQUEST, chatId);
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByUsername(auth.getName());

		if (!chatService.isMember(chat, user)) {
			ErrorParser.reject("chat.findUsers.notMember", validationResult, messageSource);

			throw new ValidationException(
					"Excption occurred while checking user for admin role to get users from chat '%s'. The user with id '%s' doesn't have it",
					validationResult, Type.FORBIDDEN, chatId, user.getId());
		}

		List<ChatUser> chatUsers = chatUserRepository.findByChat(chat);

		return chatUsers;
	}

	@Override
	public void update(ChatUser chatUser) {
		chatUserRepository.save(chatUser);
	}

}
