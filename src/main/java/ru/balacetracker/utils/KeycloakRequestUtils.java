package ru.balacetracker.utils;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.balacetracker.properties.ApplicationProperties;
import ru.balacetracker.properties.KeycloakProperties;

public class KeycloakRequestUtils {

    public static final String GRANT_TYPE_KEY = "grant_type";
    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";
    public static final String CLIENT_ID_KEY = "client_id";
    public static final String CLIENT_SECRET_KEY = "client_secret";
    public static final String REFRESH_TOKEN = "refresh_token";

    public static final String GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";
    public static final String GRANT_TYPE_PASSWORD = "password";


    public static MultiValueMap<String, String> getMultivalueMapToGetToken(String username,
                                                                           String password,
                                                                           KeycloakProperties properties) {
        MultiValueMap<String, String> mapToGetToken = new LinkedMultiValueMap<>();
        mapToGetToken.add(USERNAME_KEY, username);
        mapToGetToken.add(PASSWORD_KEY, password);
        mapToGetToken.add(GRANT_TYPE_KEY, GRANT_TYPE_PASSWORD);
        mapToGetToken.add(CLIENT_ID_KEY, properties.getResource());
        mapToGetToken.add(CLIENT_SECRET_KEY, properties.getCredentials().getSecret());
        return mapToGetToken;
    }

    public static MultiValueMap<String, String> getMultivalueMapToGetServiceToken(ApplicationProperties properties){
        MultiValueMap<String, String> mapToGetToken = new LinkedMultiValueMap<>();
        mapToGetToken.add(GRANT_TYPE_KEY, GRANT_TYPE_CLIENT_CREDENTIALS);
        mapToGetToken.add(CLIENT_ID_KEY, properties.getKeycloakRegisterClientId());
        mapToGetToken.add(CLIENT_SECRET_KEY, properties.getKeycloakRegisterClientSecret());
    return mapToGetToken;
    }

    public static String getRegisterRequestBody(String username,
                                                String email,
                                                String firstName,
                                                String lastName,
                                                String password){
        String body = "{\n" +
                "    \"enabled\": true,\n" +
                "    \"attributes\": {},\n" +
                "    \"groups\": [],\n" +
                "    \"username\": \"" + username + "\",\n" +
                "    \"emailVerified\": \"\",\n" +
                "    \"email\": \"" + email + "\",\n" +
                "    \"firstName\": \"" + firstName + "\",\n" +
                "    \"lastName\": \"" + lastName + "\",\n" +
                "    \"credentials\": [\n" +
                "        {\n" +
                "            \"type\": \"password\",\n" +
                "            \"value\": \"" + password + "\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    return body;
    }

}
