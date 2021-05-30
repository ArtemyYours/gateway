package ru.balacetracker.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.balacetracker.security.service.DefaultPrincipalFactory;
import ru.balacetracker.security.service.PrincipalFactory;

@Configuration
public class DefaultBeansConfiguration {
    @Bean
    public PrincipalFactory defaultPrincipalFactory() {
        return new DefaultPrincipalFactory();
    }
}
