package org.healthyLifestyle.authentication.service.impl;

import org.healthyLifestyle.authentication.model.ConfirmCode;
import org.healthyLifestyle.authentication.repository.ConfirmCodeRepository;
import org.healthyLifestyle.authentication.service.ConfirmCodeService;
import org.healthyLifestyle.authentication.service.generator.ConfirmCodeGenerator;
import org.healthylifestyle.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmCodeServiceImpl implements ConfirmCodeService {
	@Autowired
	private ConfirmCodeRepository codeRepository;
	@Autowired
	private ConfirmCodeGenerator codeGenerator;
	private static final Logger logger = LoggerFactory.getLogger(ConfirmCodeServiceImpl.class);

	@Override
	public ConfirmCode findByUserId(Long userId) {
		return codeRepository.findById(userId).get();
	}

	@Override
	public ConfirmCode save(User user) {
		logger.debug("Start saving confirm code");
		ConfirmCode confirmCode = new ConfirmCode();

		boolean isExist = true;
		String code = null;
		while (isExist) {
			code = codeGenerator.generate(user);
			if (!codeRepository.existsByCode(code)) {
				isExist = false;
				logger.debug("Code was found");
				break;
			}
		}

		confirmCode.setCode(code);
		confirmCode.setUser(user);

		confirmCode = codeRepository.save(confirmCode);

		logger.debug("Code has saved");

		return confirmCode;

	}

	@Override
	public void delete(ConfirmCode code) {
		codeRepository.delete(code);
	}

}
