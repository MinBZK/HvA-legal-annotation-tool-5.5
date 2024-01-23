package com.linkextractor.backend.resporitories;

import com.linkextractor.backend.models.Role;
import com.linkextractor.backend.respositories.RoleRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Test for saving a role and ensuring it is persisted in the database.
     */
    @Test
    @Transactional
    public void saveRole_ShouldPersistRole() {
        // Arrange
        Role role = new Role("USER");

        // Act
        roleRepository.save(role);

        // Assert
        assertNotNull(role.getRoleId()); // Id should be generated
        assertTrue(roleRepository.findById(role.getRoleId()).isPresent());
    }

    /**
     * Test for updating a role and verifying the update in the database.
     */
    @Test
    @Transactional
    public void updateRole_ShouldUpdateRoleInDatabase() {
        // Arrange
        Role role = new Role("USER");
        roleRepository.save(role);

        // Act
        role.setAuthority("UPDATED_ROLE");
        roleRepository.save(role);

        // Assert
        Optional<Role> updatedRole = roleRepository.findById(role.getRoleId());
        assertTrue(updatedRole.isPresent());
        assertEquals("UPDATED_ROLE", updatedRole.get().getAuthority());
    }

    /**
     * Test for deleting a role and ensuring its removal from the database.
     */
    @Test
    @Transactional
    public void deleteRole_ShouldRemoveRoleFromDatabase() {
        // Arrange
        Role role = new Role("USER");
        roleRepository.save(role);

        // Act
        roleRepository.delete(role);

        // Assert
        assertFalse(roleRepository.findById(role.getRoleId()).isPresent());
    }

    /**
     * Test for retrieving all roles and checking if they are returned correctly.
     */
    @Test
    @Transactional
    public void findAll_ShouldReturnAllRoles() {
        // Arrange
        Role role1 = new Role("ROLE1");
        Role role2 = new Role("ROLE2");
        roleRepository.saveAll(List.of(role1, role2));

        // Act
        List<Role> allRoles = roleRepository.findAll();

        // Assert
        assertEquals(4, allRoles.size());
        assertTrue(allRoles.stream().anyMatch(r -> r.getAuthority().equals("ROLE1")));
        assertTrue(allRoles.stream().anyMatch(r -> r.getAuthority().equals("ROLE2")));
    }
}
