package com.linkextractor.backend.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linkextractor.backend.models.Role;

import java.util.Optional;

/**
 * Repository interface for managing Role entities.
 * Extends JpaRepository to perform CRUD operations on Role entities.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    /**
     * Retrieves a Role entity based on the provided role authority.
     *
     * @param authority The role authority associated with the Role entity.
     * @return An Optional containing the Role entity with the specified role authority, if found.
     *         Returns an empty Optional if no Role entity matches the provided role authority.
     */
    Optional<Role> findByAuthority(String authority);
}
