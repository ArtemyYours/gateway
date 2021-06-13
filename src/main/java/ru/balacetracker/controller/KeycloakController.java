package ru.balacetracker.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.balacetracker.properties.KeycloakProperties;
import ru.balacetracker.service.RestExchangeService;
import ru.balacetracker.utils.KeycloakRequestUtils;

@RestController
@RequestMapping("/keycloak")
@RequiredArgsConstructor
public class KeycloakController {
    private final RestExchangeService restExchangeService;
    private final KeycloakProperties properties;

    private static final String GET_TOKEN_PATH = "/realms/balance-tracker/protocol/openid-connect/token";

    @PostMapping("/get-token")
    public Object getToken(
            @RequestParam @NonNull String username,
            @RequestParam @NonNull String password) {
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> mapToGetToken = KeycloakRequestUtils.getMultivalueMapToGetToken(username,
                password,
                properties);

        return restExchangeService.exchangeWithKeycloak(
                mapToGetToken,
                HttpMethod.POST,
                GET_TOKEN_PATH,
                headers,
                Object.class,
                MediaType.APPLICATION_FORM_URLENCODED
        );
    }

    @PostMapping("/register-user")
    public Object registerUser(
            @RequestParam @NonNull String username,
            @RequestParam @NonNull String firstName,
            @RequestParam @NonNull String lastName,
            @RequestParam @NonNull String email,
            @RequestParam @NonNull String password) {
        restExchangeService.registerUser(username, email, firstName, lastName, password);
        return new ResponseEntity(HttpStatus.CREATED);
    }


}
