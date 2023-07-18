package ea.slartibartfast.cardservice.domain.manager;

import ea.slartibartfast.cardservice.domain.service.CardService;
import ea.slartibartfast.cardservice.infrastructure.rest.controller.request.CreateCardRequest;
import ea.slartibartfast.cardservice.infrastructure.rest.controller.response.CreateCardResponse;
import ea.slartibartfast.cardservice.infrastructure.rest.controller.response.RetrieveCardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class CardManager {

    private final CardService cardService;

    public Mono<RetrieveCardResponse> retrieveCard(String cardToken) {
        return cardService.retrieveCard(cardToken).map(RetrieveCardResponse::new);
    }

    public Mono<CreateCardResponse> createCard(CreateCardRequest request) {
        return cardService.createCard(request).map(CreateCardResponse::new);
    }
}
