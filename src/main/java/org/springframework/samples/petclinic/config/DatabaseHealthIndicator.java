package org.springframework.samples.petclinic.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Custom health indicator to check database connectivity
 */
@Component
public class DatabaseHealthIndicator implements HealthIndicator {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseHealthIndicator.class);
    private final JdbcTemplate jdbcTemplate;

    public DatabaseHealthIndicator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Health health() {
        try {
            int result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            if (result == 1) {
                logger.debug("Database health check successful");
                return Health.up()
                    .withDetail("database", "Available")
                    .build();
            } else {
                logger.error("Database health check failed: unexpected result");
                return Health.down()
                    .withDetail("database", "Unavailable")
                    .withDetail("error", "Unexpected result from health query")
                    .build();
            }
        } catch (Exception e) {
            logger.error("Database health check failed", e);
            return Health.down()
                .withDetail("database", "Unavailable")
                .withDetail("error", e.getMessage())
                .build();
        }
    }
}