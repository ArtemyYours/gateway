package ru.balacetracker.security.utils;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.KeycloakAuthenticationException;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.balacetracker.security.model.BalanceTrackerPrincipal;

public class SecurityUtils {
    public static BalanceTrackerPrincipal getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null) {
            return new BalanceTrackerPrincipal((KeycloakPrincipal<KeycloakSecurityContext>) principal);
        }
        throw new KeycloakAuthenticationException("Security context has no keycloak principal. User is not identified");
    }

    public static String getToken() {
        SimpleKeycloakAccount currentUserAccount = (SimpleKeycloakAccount) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return currentUserAccount.getKeycloakSecurityContext().getTokenString();
    }
}
