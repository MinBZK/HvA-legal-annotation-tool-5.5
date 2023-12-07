package com.linkextractor.backend;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.linkextractor.backend.models.Role;
import com.linkextractor.backend.models.User;
import com.linkextractor.backend.respositories.RoleRepository;
import com.linkextractor.backend.respositories.UserRepository;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
	// Bean definition for initializing default roles and an admin user (For testing purposes)
	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncode){
		return args ->{
			if(roleRepository.findByAuthority("ADMIN").isPresent()) return;
			Role adminRole = roleRepository.save(new Role("ADMIN"));
			roleRepository.save(new Role("USER"));

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			// Creating an admin user with a default password (for testing purposes)
			User admin = new User(1, "admin", passwordEncode.encode("password"), "Admin@hva.nl", "A", "Admin", roles);

			userRepository.save(admin);
		};
	}

}
