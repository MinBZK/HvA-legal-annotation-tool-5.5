package com.linkextractor.backend;

import java.util.HashSet;
import java.util.Set;

import com.linkextractor.backend.models.enums.Roles;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.linkextractor.backend.models.Role;
import com.linkextractor.backend.models.User;
import com.linkextractor.backend.respositories.RoleRepository;
import com.linkextractor.backend.respositories.UserRepository;

@SpringBootApplication
@EntityScan(basePackages = "com.linkextractor.backend.models")
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	// Initializing default roles and an admin user (For testing purposes)
	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncode){
		return args ->{
			// Check if the 'ADMIN' role already exists, if so, do nothing
			if(roleRepository.findByAuthority("ADMIN").isPresent()) return;
			// Create the 'ADMIN' role and a 'USER' role if they don't exist
			Role adminRole = roleRepository.save(new Role(Roles.ADMIN.name()));
			roleRepository.save(new Role(Roles.USER.name()));

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			// Creating an admin user with default credentials (for testing purposes)
			User admin = new User(1, "admin", passwordEncode.encode("admin"), "admin@hva.nl", "admin", "admin", roles);

			userRepository.save(admin);
		};
	}

}
