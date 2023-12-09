package org.healthyLifestyle.authentication.service.builder.impl;

import org.healthyLifestyle.authentication.service.builder.MailContentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class ThymeleafMailContentBuilder implements MailContentBuilder {
	private final TemplateEngine templateEngine;
	private final String template;

	@Autowired
	public ThymeleafMailContentBuilder(TemplateEngine templateEngine, @Value("verification") String template) {
		super();
		this.templateEngine = templateEngine;
		this.template = template;
	}

	@Override
	public String build(String message) {
		Context context = new Context();
		context.setVariable("message", message);
		return templateEngine.process(template, context);
	}
}
