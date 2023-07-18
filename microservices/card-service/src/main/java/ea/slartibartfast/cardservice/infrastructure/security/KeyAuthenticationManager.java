package ea.slartibartfast.cardservice.infrastructure.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class KeyAuthenticationManager implements ReactiveAuthenticationManager {

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.fromSupplier(() -> {
            if (authentication != null && authentication.getCredentials() != null) {
                authentication.setAuthenticated(true);
            }

            return authentication;
        });
    }
}
