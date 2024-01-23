package com.linkextractor.backend.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RoleTest {

    /**
     * Test case for creating a Role and getting its authority.
     */
    @Test
    public void testRoleCreation_GetAuthority() {
        // Arrange
        String roleName = "ADMIN";

        // Act
        Role role = new Role(roleName);

        // Assert
        assertEquals(roleName, role.getAuthority());
    }

    /**
     * Test case for setting a Role's authority.
     */
    @Test
    public void testSetRoleAuthority() {
        // Arrange
        Role role = new Role();
        String roleName = "USER";

        // Act
        role.setAuthority(roleName);

        // Assert
        assertEquals(roleName, role.getAuthority());
    }

    /**
     * Test case for getting the Role's ID.
     */
    @Test
    public void testGetRoleId() {
        // Arrange
        int roleId = 42;
        Role role = new Role();
        role.setRoleId(roleId);

        // Act and Assert
        assertEquals(roleId, role.getRoleId());
    }
}
