package org.healthylifestyle.common.web;

import org.healthylifestyle.common.error.Type;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;

public class ResponseEntityResolver {
	private ResponseEntityResolver() {
	}

	public static BodyBuilder getBuilder(Type type) {
		if (type.equals(Type.BAD_REQUEST)) {
			return ResponseEntity.badRequest();
		} else if (type.equals(Type.FORBIDDEN)) {
			return ResponseEntity.status(403);
		} else {
			return ResponseEntity.status(404);
		}
	}
}
