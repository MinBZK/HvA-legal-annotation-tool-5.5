package com.linkextractor.backend.config;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.jwk.JWK;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.BandwidthBuilder;
import io.github.bucket4j.Bucket;

@Component
public class RateLimitDeterminor {
    private final int USER_ROLE_RATE = 1;
    private final int JURIST_ROLE_RATE = 1;
    private final int DEFAULT_REFRESH_RATE = 1;
    
    private final Map<String, Bucket> CACHE = new ConcurrentHashMap<>();


    public Bucket resolveBucket(String apiKey){
        return CACHE.computeIfAbsent(apiKey, this::newBucket);
    }

    public Bucket newBucket(String apiKey){
        return Bucket.builder()
        .addLimit(determineRateLimit(apiKey))
        .build();
    }

    public Bandwidth determineRateLimit(String apiKey){
        System.out.println("Ik kom hier");
        System.out.println(apiKey);
        if (apiKey.startsWith("USER")) {
            return (Bandwidth) BandwidthBuilder.builder().capacity(USER_ROLE_RATE).refillIntervally(USER_ROLE_RATE, Duration.ofHours(DEFAULT_REFRESH_RATE));
        } else if (apiKey.startsWith("JURIST")) {
            return (Bandwidth) BandwidthBuilder.builder().capacity(JURIST_ROLE_RATE).refillIntervally(JURIST_ROLE_RATE, Duration.ofHours(DEFAULT_REFRESH_RATE));
        } else {
            return null;
        }
    }
}
