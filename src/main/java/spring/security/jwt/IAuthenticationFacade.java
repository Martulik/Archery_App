package spring.security.jwt;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    public Authentication getAuthentication();
}
