package ea.slartibartfast.cardservice.infrastructure.kafka.consumer;

import ea.slartibartfast.cardservice.application.listener.CardTransactionListener;
import ea.slartibartfast.cardservice.application.model.CreateCardMessage;
import ea.slartibartfast.cardservice.domain.service.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaCardTransactionListener implements CardTransactionListener {

    private final CardService cardService;

    @KafkaListener(groupId = "card-trx", topics = "${app.kafka.topic.default-name}", containerFactory = "cardKafkaListenerContainerFactory")
    public void processCardCreate(
            @Payload CreateCardMessage payload,
            @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
            @Header(KafkaHeaders.OFFSET) Long offset,
            @Header(KafkaHeaders.RECEIVED_KEY) String messageKey) {
        log.info("Received Message: {} from partition: {}, message key is: {}", payload, partition, messageKey);

        cardService.createCard(payload);

        log.info("Card create message consumed");
    }

    @KafkaListener(groupId = "user-trx-batch", topics = "${app.kafka.topic.default-name}${app.kafka.deadletter.suffix}")
    public void processDLT(String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.warn("received DLT message : [{}] from topic : [{}] at [{}]", message, topic, LocalDateTime.now());
    }
}