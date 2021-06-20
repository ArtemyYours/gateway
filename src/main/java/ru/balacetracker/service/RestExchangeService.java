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
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import ru.balacetracker.components.RestTemplateLoggingInterceptor;
import ru.balacetracker.exceptions.RestTemplateResponseErrorHandler;
import ru.balacetracker.properties.ApplicationProperties;
import ru.balacetracker.properties.KeycloakProperties;
import ru.balacetracker.security.utils.SecurityUtils;
import ru.balacetracker.utils.KeycloakRequestUtils;

import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class RestExchangeService {
    private static final String PATH_TO_OBTAIN_ADMIN_TOKEN = "/realms/balance-tracker/protocol/openid-connect/token";

    private final ApplicationProperties applicationProperties;
    private final KeycloakProperties keycloakProperties;
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

    public <T> T exchangeWithKeycloak(Object body, HttpMethod method, String path, HttpHeaders headers, Class<T> responseClass, MediaType mediaType) {
        headers.setContentType(mediaType);
        String fullPath = keycloakProperties.getAuthServerUrl() + path;
        RestTemplate restTemplate = restTemplateBuilder
                .requestFactory(() -> new BufferingClientHttpRequestFactory(new HttpComponentsClientHttpRequestFactory()))
                .interceptors(restTemplateLoggingInterceptor)
                .errorHandler(errorHandler)
                .build();
        return restTemplate.exchange(fullPath, method, new HttpEntity<>(body, headers), responseClass).getBody();
    }

    public String getToken() {
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> mapToGetServiceToken =
                KeycloakRequestUtils.getMultivalueMapToGetServiceToken(applicationProperties);

        Map<String, String> responseEntity = exchangeWithKeycloak(mapToGetServiceToken,
                HttpMethod.POST,
                PATH_TO_OBTAIN_ADMIN_TOKEN,
                headers,
                Map.class,
                MediaType.APPLICATION_FORM_URLENCODED);
        return responseEntity.get("access_token");
    }


}
