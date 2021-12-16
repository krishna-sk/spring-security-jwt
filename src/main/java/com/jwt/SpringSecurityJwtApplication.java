package com.jwt;

import java.util.Arrays;
import java.util.HashSet;

import com.jwt.entity.User;
import com.jwt.enums.PERMISSIONS;
import com.jwt.enums.ROLES;
import com.jwt.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringSecurityJwtApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJwtApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserRepository userRepository) {
		return args -> {
			User admin_read_write = User.builder().name("admin").username("admin_read_write")
					.password(passwordEncoder.encode("password"))
					.roles(new HashSet<>(Arrays.asList(ROLES.ADMIN)))
					.permissions(new HashSet<>(Arrays.asList(PERMISSIONS.READ, PERMISSIONS.WRITE))).build();
			User admin_update_delete = User.builder().name("admin").username("admin_update_delete")
					.password(passwordEncoder.encode("password"))
					.roles(new HashSet<>(Arrays.asList(ROLES.ADMIN)))
					.permissions(new HashSet<>(Arrays.asList(PERMISSIONS.UPDATE, PERMISSIONS.DELETE))).build();

			User employee_read_write = User.builder().name("employee").username("employee_read_write")
					.password(passwordEncoder.encode("password"))
					.roles(new HashSet<>(Arrays.asList(ROLES.EMPLOYEE)))
					.permissions(new HashSet<>(Arrays.asList(PERMISSIONS.READ, PERMISSIONS.WRITE))).build();

			User employee_update_delete = User.builder().name("employee").username("employee_update_delete")
					.password(passwordEncoder.encode("password"))
					.roles(new HashSet<>(Arrays.asList(ROLES.EMPLOYEE)))
					.permissions(new HashSet<>(Arrays.asList(PERMISSIONS.UPDATE, PERMISSIONS.DELETE))).build();

			User student_read_write = User.builder().name("krishna").username("student_read_write")
					.password(passwordEncoder.encode("password"))
					.roles(new HashSet<>(Arrays.asList(ROLES.STUDENT)))
					.permissions(new HashSet<>(Arrays.asList(PERMISSIONS.READ, PERMISSIONS.WRITE))).build();

			User student_update_delete = User.builder().name("krishna").username("student_update_delete")
					.password(passwordEncoder.encode("password"))
					.roles(new HashSet<>(Arrays.asList(ROLES.STUDENT)))
					.permissions(new HashSet<>(Arrays.asList(PERMISSIONS.UPDATE, PERMISSIONS.DELETE))).build();

			userRepository.saveAll(
					Arrays.asList(admin_read_write, admin_update_delete, employee_read_write, employee_update_delete,
							student_read_write, student_update_delete));
		};
	}
}
