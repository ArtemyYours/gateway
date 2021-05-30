package ru.balacetracker.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.balacetracker.components.RestTemplateLoggingInterceptor;
import ru.balacetracker.exceptions.RestTemplateResponseErrorHandler;
import ru.balacetracker.properties.ApplicationProperties;
import ru.balacetracker.security.utils.SecurityUtils;

import java.awt.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class RestExchangeService {
    private final ApplicationProperties applicationProperties;
    private final RestTemplateBuilder restTemplateBuilder;
    private final RestTemplateLoggingInterceptor restTemplateLoggingInterceptor;
    private final RestTemplateResponseErrorHandler errorHandler = new RestTemplateResponseErrorHandler();

    public <T> T exchangeWithCrud(Object body, HttpMethod method, String path, Class<T> responseClass) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + SecurityUtils.getToken());
        if (body != null) {
            headers.setContentType(MediaType.APPLICATION_JSON);
        }
        String fullPath = applicationProperties.getCrudServerUrl() + path;
        RestTemplate restTemplate = restTemplateBuilder
                .requestFactory(() -> new BufferingClientHttpRequestFactory(new HttpComponentsClientHttpRequestFactory()))
                .interceptors(restTemplateLoggingInterceptor)
                .errorHandler(errorHandler)
                .build();
        return restTemplate.exchange(fullPath, method, new HttpEntity<>(body, headers), responseClass).getBody();
    }
}
