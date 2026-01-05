package com.cinemaabyss.proxy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "proxy")
public record ProxyProperties(
        String monolithUrl,
        String moviesUrl,
        String eventsUrl,
        boolean gradualMigration,
        int moviesMigrationPercent
) {

}
