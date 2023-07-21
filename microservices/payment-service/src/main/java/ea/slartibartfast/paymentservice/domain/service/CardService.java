package ea.slartibartfast.paymentservice.domain.service;

import ea.slartibartfast.paymentservice.application.model.CreateCardMessage;
import ea.slartibartfast.paymentservice.infrastructure.kafka.producer.CardProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CardService {

    private final CardProducer cardProducer;

    private static final String CORRELATION_ID_NAME = "correlation-id";

    public void createCard(String cardHolderName, String cardNumber) {
        var requestUUID = MDC.get(CORRELATION_ID_NAME);
        var createCardMessage = CreateCardMessage.builder()
                                                 .requestUUID(requestUUID)
                                                 .cardHolderName(cardHolderName).cardNumber(cardNumber)
                                                 .build();

        cardProducer.createCard(requestUUID, createCardMessage);
    }
}
