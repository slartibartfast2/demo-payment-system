package ea.slartibartfast.cardservice.infrastructure.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class KeyAuthenticationConverter implements ServerAuthenticationConverter {
    private static final String API_KEY_HEADER_NAME = "api-key";

    private static final String AUTH_TOKEN = "card-service-token";

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange)
                   .flatMap(serverWebExchange -> Mono.justOrEmpty(serverWebExchange.getRequest().getHeaders().get(API_KEY_HEADER_NAME)))
                   .filter(headerValues -> !headerValues.isEmpty())
                   .flatMap(headerValues -> lookup(headerValues.get(0)));
    }

    private Mono<KeyAuthenticationToken> lookup(final String apiKey) {
        if (apiKey == null) {
            var keyAuth = new KeyAuthenticationToken("", AuthorityUtils.NO_AUTHORITIES);
            return Mono.just(keyAuth);
        }

        if (!apiKey.equals(AUTH_TOKEN)) {
            throw new BadCredentialsException("Invalid API Key");
        }

        var keyAuth = new KeyAuthenticationToken(apiKey, AuthorityUtils.NO_AUTHORITIES);
        return Mono.just(keyAuth);
    }
}
