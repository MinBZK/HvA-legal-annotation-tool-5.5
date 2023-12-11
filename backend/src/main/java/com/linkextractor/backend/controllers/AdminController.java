package com.linkextractor.backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing admin-related operations.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    /**
     * Retrieves a message indicating admin-level access.
     * @return A message indicating admin access.
     */
    @GetMapping("/")
    public String getAdminAccessMessage(){
        return "Admin level access";
    }

}
