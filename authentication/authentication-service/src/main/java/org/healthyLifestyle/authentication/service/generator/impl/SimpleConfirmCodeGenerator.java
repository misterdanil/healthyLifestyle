package org.shop.authentication.service.generator.impl;

import java.util.Random;

import org.shop.authentication.service.generator.ConfirmCodeGenerator;
import org.shop.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class SimpleConfirmCodeGenerator implements ConfirmCodeGenerator {
	private int maxCharacters = 6;

	@Override
	public String generate(User user) {
		Random random = new Random();
		int max = 9;
		int min = 1;
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < 6; i++) {
			int number = random.nextInt(max - min + 1) + min;
			builder.append(number);

		}

		return builder.toString();
	}

	public int getMaxCharacters() {
		return maxCharacters;
	}

	public void setMaxCharacters(int maxCharacters) {
		this.maxCharacters = maxCharacters;
	}

}
