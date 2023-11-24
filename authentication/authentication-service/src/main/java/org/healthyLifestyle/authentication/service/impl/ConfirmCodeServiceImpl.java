package org.shop.authentication.service.impl;

import org.shop.authentication.model.ConfirmCode;
import org.shop.authentication.repository.ConfirmCodeRepository;
import org.shop.authentication.service.ConfirmCodeService;
import org.shop.authentication.service.generator.ConfirmCodeGenerator;
import org.shop.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmCodeServiceImpl implements ConfirmCodeService {
	@Autowired
	private ConfirmCodeRepository codeRepository;
	@Autowired
	private ConfirmCodeGenerator codeGenerator;

	@Override
	public ConfirmCode findByUserId(Long userId) {
		return codeRepository.findById(userId).get();
	}

	@Override
	public ConfirmCode save(User user) {
		ConfirmCode confirmCode = new ConfirmCode();

		boolean isExist = true;
		String code = null;
		while (isExist) {
			code = codeGenerator.generate(user);
			if (!codeRepository.existsByCode(code)) {
				isExist = false;
				break;
			}
		}

		confirmCode.setCode(code);
		confirmCode.setUser(user);

		confirmCode = codeRepository.save(confirmCode);

		return confirmCode;

	}

}
