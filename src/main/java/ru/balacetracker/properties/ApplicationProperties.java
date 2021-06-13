package ru.balacetracker.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.Base64;

@Getter
@Setter
@Component
@ConfigurationProperties("application")
public class ApplicationProperties {
    private String crudServerUrl;
    private Integer logBodyLimit;
    private String keycloakUrlXforward;
    private String keycloakRegisterClientId;
    private String keycloakRegisterClientSecret;
}
