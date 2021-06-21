package ru.balacetracker.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.balacetracker.properties.KeycloakProperties;
import ru.balacetracker.security.utils.SecurityUtils;
import ru.balacetracker.service.RestExchangeService;
import ru.balacetracker.utils.KeycloakRequestUtils;

@RestController
@RequestMapping("/keycloak")
@RequiredArgsConstructor
public class KeycloakController {
    private final RestExchangeService restExchangeService;
    private final KeycloakProperties properties;


    private static final String PATH_TO_LOGOUT = "/realms/balance-tracker/protocol/openid-connect/logout";
    private static final String GET_TOKEN_PATH = "/realms/balance-tracker/protocol/openid-connect/token";
    private static final String PATH_TO_REGISTER_USER = "/admin/realms/balance-tracker/users";
    private static final String REFRESH_TOKEN_PATH = "realms/balance-tracker/protocol/openid-connect/token";

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

        String clientToken = restExchangeService.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + clientToken);

        String body = KeycloakRequestUtils.getRegisterRequestBody(username, email, firstName, lastName, password);
        restExchangeService.exchangeWithKeycloak(body,
                HttpMethod.POST,
                PATH_TO_REGISTER_USER,
                headers,
                null,
                MediaType.APPLICATION_JSON);
        return new ResponseEntity(HttpStatus.CREATED);
    }


    @PostMapping("/logout")
    public void logout(){
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + SecurityUtils.getToken());

        MultiValueMap<String, String> body =
                new LinkedMultiValueMap<>();
        body.add(KeycloakRequestUtils.CLIENT_ID_KEY, properties.getResource());
        body.add(KeycloakRequestUtils.CLIENT_SECRET_KEY, properties.getCredentials().getSecret());

                restExchangeService.exchangeWithKeycloak(body,
                HttpMethod.POST,
                PATH_TO_LOGOUT,
                headers,
                null,
                MediaType.APPLICATION_FORM_URLENCODED);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(@RequestParam @NonNull String refreshToken){
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + SecurityUtils.getToken());

        MultiValueMap<String, String> body =
                new LinkedMultiValueMap<>();
        body.add(KeycloakRequestUtils.CLIENT_ID_KEY, properties.getResource());
        body.add(KeycloakRequestUtils.REFRESH_TOKEN_KEY, refreshToken);
        body.add(KeycloakRequestUtils.GRANT_TYPE_KEY, KeycloakRequestUtils.REFRESH_TOKEN_KEY);
        body.add(KeycloakRequestUtils.CLIENT_SECRET_KEY, properties.getCredentials().getSecret());

        restExchangeService.exchangeWithKeycloak(body,
                HttpMethod.POST,
                REFRESH_TOKEN_PATH,
                headers,
                null,
                MediaType.APPLICATION_FORM_URLENCODED);
    }


}
