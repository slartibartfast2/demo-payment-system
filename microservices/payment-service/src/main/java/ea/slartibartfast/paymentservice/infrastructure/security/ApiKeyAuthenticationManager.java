package ea.slartibartfast.paymentservice.infrastructure.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ApiKeyAuthenticationManager implements AuthenticationManager {

    private static final String AUTH_TOKEN = "payment-token";

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        String apiKey = Optional.of(authentication)
                                .map(Authentication::getPrincipal)
                                .map(Object::toString)
                                .orElse(null);

        if (apiKey == null) {
            return authentication;
        }

        if (!apiKey.equals(AUTH_TOKEN)) {
            throw new BadCredentialsException("Invalid API Key");
        }

        return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
    }

}
