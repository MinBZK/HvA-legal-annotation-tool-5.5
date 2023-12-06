package com.linkextractor.backend.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RateLimitInterceptor implements HandlerInterceptor{

    // @Override
    // public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
    //     String apiKey = request.getHeader("X-api-key");
    //     if (apiKey.isBlank()) {
    //         response.sendError(HttpStatus.BAD_REQUEST.value(),"Missing Header: X-api-key");
    //         return apiKey.isBlank();
    //     }

    // }
}
