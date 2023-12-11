package org.healthylifestyle.app;

import org.healthylifestyle.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import jakarta.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = { "org.healthyLifestyle", "org.healthylifestyle" })
@EnableJpaRepositories(basePackages = "org.healthyLifestyle")
@EntityScan(basePackages = "org.healthyLifestyle")
public class Main {
	@Autowired
	private RoleService roleService;

	public static void main(String[] args) {
		ApplicationContext ac = SpringApplication.run(Main.class, args);
	}

	@PostConstruct
	public void run() {
//		roleService.save("ROLE_USER");
//		roleService.save("ROLE_OAUTH2_USER");
	}
}
