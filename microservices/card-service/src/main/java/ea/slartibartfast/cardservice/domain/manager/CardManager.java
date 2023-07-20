package ea.slartibartfast.cardservice.domain.manager;

import ea.slartibartfast.cardservice.domain.service.CardService;
import ea.slartibartfast.cardservice.infrastructure.rest.controller.response.RetrieveCardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CardManager {

    private final CardService cardService;

    public RetrieveCardResponse retrieveCard(String cardToken) {
        var cardVo = cardService.retrieveCard(cardToken);
        return new RetrieveCardResponse(cardVo);
    }
}
