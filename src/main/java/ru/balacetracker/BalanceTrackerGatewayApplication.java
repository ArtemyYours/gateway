package ru.balacetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.balacetracker.properties.ApplicationProperties;
import ru.balacetracker.properties.KeycloakClientCredentials;
import ru.balacetracker.properties.KeycloakProperties;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class, KeycloakProperties.class, KeycloakClientCredentials.class})
public class BalanceTrackerGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(BalanceTrackerGatewayApplication.class, args);
    }

}
