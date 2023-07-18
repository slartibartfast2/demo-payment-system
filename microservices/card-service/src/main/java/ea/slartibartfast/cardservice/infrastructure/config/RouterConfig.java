package ea.slartibartfast.cardservice.infrastructure.config;

import ea.slartibartfast.cardservice.infrastructure.handler.CardHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    private static final String PREFIX = "/api/v1/cards/";

    @Bean
    RouterFunction<ServerResponse> routes(CardHandler handler) {
        return route(GET(PREFIX + "{cardToken}"), handler::retrieveCard)
                .and(route(POST(PREFIX).and(accept(MediaType.APPLICATION_JSON)), handler::create));
    }
}

