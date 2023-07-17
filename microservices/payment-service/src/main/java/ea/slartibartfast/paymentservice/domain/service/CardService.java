package ea.slartibartfast.paymentservice.domain.service;

import ea.slartibartfast.paymentservice.infrastructure.client.CardClient;
import ea.slartibartfast.paymentservice.infrastructure.client.request.CreateCardRequest;
import ea.slartibartfast.paymentservice.infrastructure.client.response.CreateCardResponse;
import ea.slartibartfast.paymentservice.infrastructure.client.response.RetrieveCardResponse;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CardService {

    private final CardClient cardClient;

    private static final String CARD_TOKEN = "card-service-token";
    private static final String CORRELATION_ID_NAME = "correlation-id";

    @Retry(name = "cardService")
    public CreateCardResponse createCard(String cardHolderName, String cardNumber) {
        var createCardRequest = CreateCardRequest.builder()
                                                 .cardHolderName(cardHolderName).cardNumber(cardNumber)
                                                 .build();

        return cardClient.createCard(CARD_TOKEN, MDC.get(CORRELATION_ID_NAME), createCardRequest);
    }

    @Retry(name = "cardService")
    public RetrieveCardResponse retrieveCardResponse(String cardToken) {
        return cardClient.retrieveCard(CARD_TOKEN, MDC.get(CORRELATION_ID_NAME), cardToken);
    }
}
