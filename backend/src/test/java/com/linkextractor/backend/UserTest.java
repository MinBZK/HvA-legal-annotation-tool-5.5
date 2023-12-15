package com.linkextractor.backend;

import com.linkextractor.backend.models.Role;
import com.linkextractor.backend.models.User;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    @Test
    public void testUserCreationWithSingleRole() {
        // Arrange
        Role role = new Role("Admin");
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        // Act
        User user = new User(1, "admin", "admin", "admin@hva.nl", "John", "Doe", roles);

        // Assert
        assertEquals(1, user.getUserId());
        assertEquals("admin", user.getUsername());
        assertEquals("admin", user.getPassword());
        assertEquals("admin@hva.nl", user.getEmail());
        assertEquals("John", user.getFirstname());
        assertEquals("Doe", user.getLastname());
        assertEquals(roles, user.getAuthorities());
    }


    @Test
    public void testUserWithNoRole() {
        // Arrange
        Set<Role> roles = new HashSet<>();

        // Act
        User user = new User();
        user.setAuthorities(roles);

        // Assert
        assertEquals(roles, user.getAuthorities());
    }

    @Test
    public void testUserWithSingleRole() {
        // Arrange
        Role userRole = new Role("USER");
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        // Act
        User user = new User();
        user.setAuthorities(roles);

        // Assert
        assertEquals(roles, user.getAuthorities());
    }

    @Test
    public void testUserWithMultipleRoles() {
        // Arrange
        Role userRole = new Role("USER");
        Role adminRole = new Role("ADMIN");
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        roles.add(adminRole);

        // Act
        User user = new User();
        user.setAuthorities(roles);

        // Assert
        assertEquals(roles, user.getAuthorities());
    }
}
