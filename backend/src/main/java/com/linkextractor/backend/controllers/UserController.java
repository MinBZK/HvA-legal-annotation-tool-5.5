package com.linkextractor.backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing user-related operations.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * Retrieves a message indicating user-level access.
     * @return A message indicating user access.
     */
    @GetMapping("/")
    public String getUserAccessMessage(){
        return "User access level";
    }

}
