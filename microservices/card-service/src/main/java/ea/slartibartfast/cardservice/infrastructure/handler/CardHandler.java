package ea.slartibartfast.cardservice.infrastructure.handler;

import ea.slartibartfast.cardservice.domain.manager.CardManager;
import ea.slartibartfast.cardservice.infrastructure.rest.controller.request.CreateCardRequest;
import ea.slartibartfast.cardservice.infrastructure.rest.controller.response.CreateCardResponse;
import ea.slartibartfast.cardservice.infrastructure.rest.controller.response.RetrieveCardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CardHandler {

    private final CardManager cardManager;

    public Mono<ServerResponse> retrieveCard(ServerRequest request) {
        return ServerResponse.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(
                                     cardManager.retrieveCard(request.pathVariable("cardToken")),
                                     RetrieveCardResponse.class
                             );
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<CreateCardRequest> requestMono = request.bodyToMono(CreateCardRequest.class);

        return requestMono
                .flatMap(r -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(cardManager.createCard(r), CreateCardResponse.class)
                );
    }
}
