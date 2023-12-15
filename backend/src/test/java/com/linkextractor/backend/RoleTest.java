package com.linkextractor.backend;

import com.linkextractor.backend.models.Role;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoleTest {

    @Test
    public void testRoleCreation() {
        // Arrange
        String roleName = "ADMIN";

        // Act
        Role role = new Role(roleName);

        // Assert
        assertEquals(roleName, role.getAuthority());
    }

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
}
