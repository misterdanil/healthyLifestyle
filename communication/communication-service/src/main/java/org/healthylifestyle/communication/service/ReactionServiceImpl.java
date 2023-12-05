package org.healthylifestyle.communication.service;

import org.healthylifestyle.common.error.BindingResultFactory;
import org.healthylifestyle.common.error.Type;
import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.common.web.ErrorParser;
import org.healthylifestyle.communication.model.Chat;
import org.healthylifestyle.communication.model.ChatUser;
import org.healthylifestyle.communication.model.Message;
import org.healthylifestyle.communication.model.Reaction;
import org.healthylifestyle.communication.repository.ReactionRepository;
import org.healthylifestyle.user.model.User;
import org.healthylifestyle.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class ReactionServiceImpl implements ReactionService {
	@Autowired
	private ReactionRepository reactionRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private ChatUserService chatUserService;
	@Autowired
	private MessageSource messageSource;

	@Override
	public Reaction view(Long messageId) throws ValidationException {
		BindingResult validationResult = BindingResultFactory.getInstance("message");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByUsername(auth.getName());

		Message message = messageService.findById(messageId);
		if (message == null) {
			ErrorParser.reject("message.view.notFound", validationResult, messageSource);
		}
		ErrorParser.checkErrors(validationResult,
				"Exception occurred while viewing a message with id '%s'. This doesn't exist", Type.BAD_REQUEST,
				messageId);

		Chat chat = message.getChat();

		ChatUser chatUser = chatUserService.findByChatAndUser(chat.getId(), user.getId());
		if (chatUser == null) {
			ErrorParser.reject("message.view.chat.notMember", validationResult, messageSource, chat.getId());
		}
		ErrorParser.checkErrors(validationResult,
				"Exception occurred while viewing a message with id '%s'. The user '%s' isn't member of this chat '%s'",
				Type.FORBIDDEN, messageId, user.getId(), chat.getId());

		if (reactionRepository.existsByUser(chatUser, message)) {
			ErrorParser.reject("message.view.reaction.exist", validationResult, messageSource);
		}
		ErrorParser.checkErrors(validationResult,
				"Exception occurred while viewing a message with id '%s'. The user '%s' has already viewed it",
				Type.BAD_REQUEST, messageId, user.getId());

		Reaction reaction = new Reaction();
		reaction.setChatUser(chatUser);
		reaction.setMessage(message);

		reaction = reactionRepository.save(reaction);

		return reaction;
	}

	@Override
	public boolean existsByUser(ChatUser chatUser, Message message) {
		return reactionRepository.existsByUser(chatUser, message);
	}
}
