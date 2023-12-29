package org.healthylifestyle.app;

import org.healthylifestyle.copywriting.model.Category;
import org.healthylifestyle.copywriting.service.CategoryService;
import org.healthylifestyle.user.model.Role;
import org.healthylifestyle.user.model.User;
import org.healthylifestyle.user.model.lifestyle.healthy.ClassType;
import org.healthylifestyle.user.model.lifestyle.healthy.ParameterType;
import org.healthylifestyle.user.model.lifestyle.healthy.measure.Measure;
import org.healthylifestyle.user.model.lifestyle.healthy.measure.Type;
import org.healthylifestyle.user.model.lifestyle.healthy.measure.Unit;
import org.healthylifestyle.user.service.ParameterTypeService;
import org.healthylifestyle.user.service.RoleService;
import org.healthylifestyle.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = { "org.healthyLifestyle", "org.healthylifestyle" })
@EnableJpaRepositories(basePackages = "org.healthyLifestyle")
@EntityScan(basePackages = "org.healthyLifestyle")
public class Main {
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ParameterTypeService parameterTypeService;
	@Autowired
	private SimpMessagingTemplate t;

	public static void main(String[] args) {
		ApplicationContext ac = SpringApplication.run(Main.class, args);
		SecurityFilterChain c = (SecurityFilterChain) ac.getBean("filterChain");
		Object o = c.getFilters();
		System.out.println();
	}

	@PostConstruct
	public void run() {
//		roleService.save("ROLE_USER");
//		roleService.save("ROLE_COPYWRITER");
//		roleService.save("ROLE_OAUTH2_USER");
//		roleService.save("ROLE_CHAT_OWNER");
//		roleService.save("ROLE_CHAT_ADMIN");
//		roleService.save("ROLE_CHAT_MEMBER");

//		Role role = roleService.findByName("ROLE_COPYWRITER");
//		User user = userService.findById(1L);
//		if (user != null) {
//			user.addRole(role);
//
//			userService.save(user);
//		}

//		Category category = new Category();
//		category.setOriginalTitle("Упражнения");
//		categoryService.save(category);
//
//		ParameterType pt1 = new ParameterType();
//		pt1.setOriginalTitle("Рост");
//		pt1.setType(ClassType.DOUBLE);
//		Measure m1 = new Measure();
//		m1.setType(Type.WEIGHT);
//		m1.setUnit(Unit.METER);
//		pt1.setMeasure(m1);
//
//		ParameterType pt2 = new ParameterType();
//		pt2.setOriginalTitle("Вес");
//		pt2.setType(ClassType.DOUBLE);
//		Measure m2 = new Measure();
//		m2.setType(Type.WEIGHT);
//		m2.setUnit(Unit.KG);
//		pt2.setMeasure(m2);
//
//		ParameterType pt3 = new ParameterType();
//		pt3.setOriginalTitle("Уровень сахара");
//		pt3.setType(ClassType.DOUBLE);
//		Measure m3 = new Measure();
//		m3.setType(Type.SUGAR);
//		m3.setUnit(Unit.mmol_L);
//		pt3.setMeasure(m3);
//
//		parameterTypeService.save(pt1);
//		parameterTypeService.save(pt2);
//		parameterTypeService.save(pt3);
	}
}
