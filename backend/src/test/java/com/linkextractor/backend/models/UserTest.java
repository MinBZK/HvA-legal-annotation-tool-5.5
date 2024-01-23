package com.linkextractor.backend.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserTest {

    /**
     * Tests the creation of a user with a single role and ensures that attributes are set correctly.
     */
    @Test
    public void createUserWithSingleRole_ShouldSetAttributesCorrectly() {
        // Arrange
        Role adminRole = new Role("Admin");
        Set<Role> roles = new HashSet<>();
        roles.add(adminRole);

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

    /**
     * Tests the creation of a user with no roles and ensures that the authorities are empty.
     */
    @Test
    public void createUserWithNoRole_ShouldHaveEmptyAuthorities() {
        // Arrange
        Set<Role> roles = new HashSet<>();

        // Act
        User user = new User();
        user.setAuthorities(roles);

        // Assert
        assertEquals(roles, user.getAuthorities());
    }

    /**
     * Tests the creation of a user with a single role and ensures that authorities are set correctly.
     */
    @Test
    public void createUserWithSingleRole_ShouldSetAuthoritiesCorrectly() {
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

    /**
     * Tests the creation of a user with multiple roles and ensures that authorities are set correctly.
     */
    @Test
    public void createUserWithMultipleRoles_ShouldSetAuthoritiesCorrectly() {
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
