package com.linkextractor.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RateLimitInterceptor implements HandlerInterceptor{
    private RateLimitDeterminor rateLimitDeterminor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String apiKey = request.getHeader("X-api-key");
        if (apiKey.isBlank()) {
            response.sendError(HttpStatus.BAD_REQUEST.value(),"Missing Header: X-api-key");
            return apiKey.isBlank();
        }

        System.out.println("Hoi");

        Bucket tokenBucket = rateLimitDeterminor.resolveBucket(apiKey);
        ConsumptionProbe probe = tokenBucket.tryConsumeAndReturnRemaining(1);

        if (probe.isConsumed()) {
            System.out.println("If error");
            response.addHeader("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));
            return true; 
        } else {
            System.out.println("Else error");
            long waitForRefill = probe.getNanosToWaitForRefill();
            response.addHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitForRefill));
            response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(),"You have send to many API Requests");
            return false;
        }
    }
}
