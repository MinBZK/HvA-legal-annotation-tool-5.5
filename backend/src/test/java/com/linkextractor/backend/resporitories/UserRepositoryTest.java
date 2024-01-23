package com.linkextractor.backend.resporitories;

import com.linkextractor.backend.models.Role;
import com.linkextractor.backend.models.User;
import com.linkextractor.backend.respositories.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    /**
     * Tests the successful retrieval of a user by their username.
     */
    @Test
    @Transactional
    public void findByUsername_ShouldReturnUserByUsername() {
        // Arrange
        Set<Role> roles = Set.of(new Role("USER"));
        User user = new User(0, "testUser", "password", "test@hva.nl", "John", "Doe", roles);
        userRepository.save(user);

        // Act
        Optional<User> foundUserOptional = userRepository.findByUsername("testUser");

        // Assert
        assertTrue(foundUserOptional.isPresent());
        assertEquals(user.getUsername(), foundUserOptional.get().getUsername());
    }

    /**
     * Tests the successful retrieval of a user by their email.
     */
    @Test
    @Transactional
    public void findByEmail_ShouldReturnUserByEmail() {
        // Arrange
        Set<Role> roles = Set.of(new Role("USER"));
        User user = new User(0, "testUser", "password", "test@hva.nl", "John", "Doe", roles);
        userRepository.save(user);

        // Act
        Optional<User> foundUserOptional = userRepository.findByEmail("test@hva.nl");

        // Assert
        assertTrue(foundUserOptional.isPresent());
        assertEquals(user.getEmail(), foundUserOptional.get().getEmail());
    }

    /**
     * Tests the scenario where attempting to find a non-existing username returns an empty Optional.
     */
    @Test
    @Transactional
    public void findByNonExistingUsername_ShouldReturnEmptyOptional() {
        // Act
        Optional<User> foundUserOptional = userRepository.findByUsername("nonExistingUser");

        // Assert
        assertTrue(foundUserOptional.isEmpty());
    }

    /**
     * Tests the scenario where attempting to find a user by a non-existing email returns an empty Optional.
     */
    @Test
    @Transactional
    public void findByNonExistingEmail_ShouldReturnEmptyOptional() {
        // Act
        Optional<User> foundUserOptional = userRepository.findByEmail("nonexisting@hva.nl");

        // Assert
        assertTrue(foundUserOptional.isEmpty());
    }

    /**
     * Tests the successful retrieval of all users from the database.
     */
    @Test
    @Transactional
    public void findAll_ShouldReturnAllUsers() {
        // Arrange
        Set<Role> roles = Set.of(new Role("USER"));
        User user1 = new User(0, "user1", "password", "user1@hva.nl", "User", "One", roles);
        User user2 = new User(0, "user2", "password", "user2@hva.nl", "User", "Two", roles);
        userRepository.saveAll(List.of(user1, user2));

        // Act
        List<User> allUsers = userRepository.findAll();

        // Assert
        long expectedSize = userRepository.count(); // Count all existing records in the database
        assertEquals(expectedSize, allUsers.size());
        assertTrue(allUsers.stream().anyMatch(u -> u.getUsername().equals("user1")));
        assertTrue(allUsers.stream().anyMatch(u -> u.getUsername().equals("user2")));
    }

    /**
     * Tests the removal of a user from the database.
     */
    @Test
    @Transactional
    public void deleteUser_ShouldRemoveUserFromDatabase() {
        // Arrange
        Set<Role> roles = Set.of(new Role("USER"));
        User user = new User(0, "toBeDeleted", "password", "delete@hva.com", "Delete", "Me", roles);
        userRepository.save(user);

        // Act
        userRepository.delete(user);

        // Assert
        assertFalse(userRepository.findById(user.getUserId()).isPresent());
    }
}
