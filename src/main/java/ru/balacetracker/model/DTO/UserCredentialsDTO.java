package ru.balacetracker.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.balacetracker.properties.KeycloakProperties;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentialsDTO {
    private String username;
    private String password;
    private String grantType;
    private String clientId;
    private String clientSecret;

    private UserCredentialsDTO(String username,
                               String password,
                               KeycloakProperties properties) {
        this.username = username;
        this.password = password;
        this.grantType = "password";
        this.clientId = properties.getResource();
        this.clientSecret = properties.getCredentials().getSecret();
    }

    //TODO do it in correct way, without static and this UserCredentialDTO class
    public static MultiValueMap<String, String> getMultivalueMap(String username,
                                                          String password,
                                                          KeycloakProperties properties) {
        UserCredentialsDTO userCredentialsDTO = new UserCredentialsDTO(username, password, properties);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", userCredentialsDTO.username);
        map.add("password", userCredentialsDTO.password);
        map.add("grant_type", userCredentialsDTO.grantType);
        map.add("client_id", userCredentialsDTO.clientId);
        map.add("client_secret", userCredentialsDTO.clientSecret);
        return map;
    }


}
