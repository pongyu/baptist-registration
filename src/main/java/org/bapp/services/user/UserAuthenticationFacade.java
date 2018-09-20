package org.bapp.services.user;

import org.springframework.security.core.Authentication;

public interface UserAuthenticationFacade {
    Authentication getAuthentication();
}
