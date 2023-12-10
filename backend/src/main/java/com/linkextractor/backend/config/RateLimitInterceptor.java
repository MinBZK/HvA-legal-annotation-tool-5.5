package com.linkextractor.backend.config;

import org.springframework.context.annotation.Configuration;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

    private RateLimitDeterminor rateLimitDeterminor;
import jakarta.servlet.http.HttpServletRequest;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String apiKey = request.getHeader("X-api-key");
        if (apiKey.isBlank()) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), "Missing Header: X-api-key");
            return apiKey.isBlank();
        }

    }
}
