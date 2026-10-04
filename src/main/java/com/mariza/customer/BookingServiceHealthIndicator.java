
package com.mariza.customer;

import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BookingServiceHealthIndicator implements HealthIndicator {

    private final RestTemplate restTemplate;

    public BookingServiceHealthIndicator(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Health health() {

        try {

            ResponseEntity<String> response =
                    restTemplate.getForEntity(
                            "http://localhost:8080/actuator/health",
                            String.class
                    );

            if (response.getStatusCode().is2xxSuccessful()) {
                return Health.up()
                        .withDetail("booking-service", "Available")
                        .build();
            }

            return Health.down()
                    .withDetail("booking-service", "Unreachable")
                    .build();

        } catch (Exception e) {

            return Health.down()
                    .withDetail("booking-service", e.getMessage())
                    .build();
        }
    }
}