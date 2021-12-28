package com.jwt;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jwt.entity.User;
import com.jwt.enums.PERMISSIONS;
import com.jwt.enums.ROLES;
import com.jwt.repository.UserRepository;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Spring Security JWT API", version = "1.0", description = "Spring Security JWT Application", 
termsOfService = "http://swagger.io/terms/", license = @License(name = "Apache 2.0", url = "http://springdoc.org")))
public class SpringSecurityJwtApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	private static final String PASSWORD = "password";

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJwtApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserRepository userRepository) {
		return args -> {
			User adminReadWrite = User.builder().name("admin").username("admin_read_write")
					.password(passwordEncoder.encode(PASSWORD)).roles(new HashSet<>(Arrays.asList(ROLES.ADMIN)))
					.permissions(new HashSet<>(Arrays.asList(PERMISSIONS.READ, PERMISSIONS.WRITE))).build();
			User adminUpdateDelete = User.builder().name("admin").username("admin_update_delete")
					.password(passwordEncoder.encode(PASSWORD)).roles(new HashSet<>(Arrays.asList(ROLES.ADMIN)))
					.permissions(new HashSet<>(Arrays.asList(PERMISSIONS.UPDATE, PERMISSIONS.DELETE))).build();

			User employeeReadWrite = User.builder().name("employee").username("employee_read_write")
					.password(passwordEncoder.encode(PASSWORD)).roles(new HashSet<>(Arrays.asList(ROLES.EMPLOYEE)))
					.permissions(new HashSet<>(Arrays.asList(PERMISSIONS.READ, PERMISSIONS.WRITE))).build();

			User employeeUpdateDelete = User.builder().name("employee").username("employee_update_delete")
					.password(passwordEncoder.encode(PASSWORD)).roles(new HashSet<>(Arrays.asList(ROLES.EMPLOYEE)))
					.permissions(new HashSet<>(Arrays.asList(PERMISSIONS.UPDATE, PERMISSIONS.DELETE))).build();

			User studentReadWrite = User.builder().name("student").username("student_read_write")
					.password(passwordEncoder.encode(PASSWORD)).roles(new HashSet<>(Arrays.asList(ROLES.STUDENT)))
					.permissions(new HashSet<>(Arrays.asList(PERMISSIONS.READ, PERMISSIONS.WRITE))).build();

			User studentUpdateDelete = User.builder().name("student").username("student_update_delete")
					.password(passwordEncoder.encode(PASSWORD)).roles(new HashSet<>(Arrays.asList(ROLES.STUDENT)))
					.permissions(new HashSet<>(Arrays.asList(PERMISSIONS.UPDATE, PERMISSIONS.DELETE))).build();

			userRepository.saveAll(Arrays.asList(adminReadWrite, adminUpdateDelete, employeeReadWrite,
					employeeUpdateDelete, studentReadWrite, studentUpdateDelete));
		};
	}

//	@Bean
//	public OpenAPI customOpenAPI(@Value("${application.description}") String appDesciption,
//			@Value("${application.version}") String appVersion) {
//		return new OpenAPI().info(new Info().title("Spring Security JWT API").version(appVersion)
//				.description(appDesciption).termsOfService("http://swagger.io/terms/")
//				.license(new License().name("Apache 2.0").url("http://springdoc.org")));
//	}
}
