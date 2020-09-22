package com.microservices.localidades.config;

import com.microservices.localidades.endpoint.service.IBGEApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author Jônatas Tonholo
 * A class with schedules to run periodically
 */
@Configuration
@EnableScheduling
@Slf4j
public class SchedulesConfig {

    @Autowired
    private IBGEApiService _IBGEApiService;

    /**
     * Automatic cache cleaning each 1 hour
     */
    @Scheduled(fixedDelay = 3600000L, initialDelay = 0L) // Clear the cache after 60 min (3600000L ms)
    private void cleanCache() {
        log.info("SchedulesConfig.cleanCache");
        log.info("Cache cleaned by configurated timeout");
        _IBGEApiService.cleanLocalidadesCache();
    }
}
