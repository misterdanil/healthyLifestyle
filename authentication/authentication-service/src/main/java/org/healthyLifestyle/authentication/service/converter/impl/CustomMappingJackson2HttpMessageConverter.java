package org.healthyLifestyle.authentication.service.converter.impl;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

//@Component
public class CustomMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

	@Override
	public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		Map<?, ?> result = (Map<?, ?>) super.read(type, contextClass, inputMessage);

		if (result.containsKey("response") && result.get("response") instanceof List) {
			List<?> userInfoWrap = (List<?>) result.get("response");
			Object userInfo = userInfoWrap.get(0);
			return userInfo;
		}

		return result;
	}

}
