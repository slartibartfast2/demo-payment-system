package ea.slartibartfast.cardservice.infrastructure.config;

import ea.slartibartfast.cardservice.infrastructure.security.KeyAuthenticationConverter;
import ea.slartibartfast.cardservice.infrastructure.security.KeyAuthenticationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
            // other public endpoints of your API may be appended to this array
            "/actuator/**"
    };


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         KeyAuthenticationManager keyAuthenticationManager,
                                                         KeyAuthenticationConverter keyAuthenticationConverter) {
        final AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(keyAuthenticationManager);
        authenticationWebFilter.setServerAuthenticationConverter(keyAuthenticationConverter);

        // @formatter:off
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(request -> request.pathMatchers(AUTH_WHITELIST).permitAll()
                                                     .anyExchange().authenticated())
                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION);
        // @formatter:on
        return http.build();
    }
}
