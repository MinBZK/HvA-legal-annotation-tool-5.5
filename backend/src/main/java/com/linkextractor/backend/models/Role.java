package com.linkextractor.backend.models;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity representing a role in the application.
 * Implements GrantedAuthority to integrate with Spring Security for role-based authorization.
 */
@Entity
@Table(name="role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="role_id")
    private int roleId;

    private String authority;

    public Role(){
    }

    public Role(String authority){
        this.authority = authority;
    }

    public Role(int roleId, String authority){
        this.roleId = roleId;
        this.authority = authority;
    }

    // Getters and Setters
    @Override
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority){
        this.authority = authority;
    }

    public int getRoleId(){
        return this.roleId;
    }

    public void setRoleId(int roleId){
        this.roleId = roleId;
    }
}
