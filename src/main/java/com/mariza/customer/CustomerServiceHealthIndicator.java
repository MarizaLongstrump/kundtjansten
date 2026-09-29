package com.mariza.customer;

import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.stereotype.Component;


@Component
    public class CustomerServiceHealthIndicator implements HealthIndicator {

        @Override
        public Health health() {
            return Health.up()
                    .withDetail("service", "Customer Service is running")
                    .build();
        }
    }


