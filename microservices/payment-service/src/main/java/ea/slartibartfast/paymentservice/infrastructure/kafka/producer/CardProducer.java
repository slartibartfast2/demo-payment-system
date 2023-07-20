package ea.slartibartfast.paymentservice.infrastructure.kafka.producer;

import ea.slartibartfast.paymentservice.application.model.BalanceUpdateMessage;
import ea.slartibartfast.paymentservice.application.model.CreateCardMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class CardProducer {

    private final KafkaTemplate<String, CreateCardMessage> createCardKafkaTemplate;

    @Value("${app.kafka.card.topic.default-name}")
    private String topicName;

    public void createCard(String uuid, CreateCardMessage createCardMessage) {
        log.info("Sending message='{}' to topic='{}'", createCardMessage, topicName);
        createCardKafkaTemplate.send(topicName,
                                     uuid,
                                     createCardMessage);
    }
}
