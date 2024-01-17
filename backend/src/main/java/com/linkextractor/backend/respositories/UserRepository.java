package com.linkextractor.backend.respositories;

import com.linkextractor.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing User entities.
 * Extends JpaRepository to perform CRUD operations on User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Retrieves a User entity based on the provided username.
     *
     * @param username The username associated with the User entity.
     * @return An Optional containing the User entity with the specified username, if found.
     * Returns an empty Optional if no User entity matches the provided username.
     */
    Optional<User> findByUsername(String username);

    /**
     * Retrieves a User entity based on the provided username.
     *
     * @param username The username associated with the User entity.
     * @return The User entity with the specified username, if found.
     * Returns null if no User entity matches the provided username.
     */
    User findUserByUsername(String username);

    /**
     * Retrieves a User entity based on the provided email address.
     *
     * @param email The email address associated with the User entity.
     * @return An Optional containing the User entity with the specified email address, if found.
     * Returns an empty Optional if no User entity matches the provided email address.
     */
    Optional<User> findByEmail(String email);
}
