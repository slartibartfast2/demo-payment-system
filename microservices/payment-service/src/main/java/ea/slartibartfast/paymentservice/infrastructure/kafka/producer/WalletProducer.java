package ea.slartibartfast.paymentservice.infrastructure.kafka.producer;

import ea.slartibartfast.paymentservice.application.model.BalanceUpdateMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class WalletProducer {

    private final KafkaTemplate<String, BalanceUpdateMessage> walletUpdateBalanceKafkaTemplate;

    @Value("${app.kafka.wallet.topic.default-name}")
    private String topicName;

    public void sendBalanceUpdate(String uuid, BalanceUpdateMessage balanceUpdateMessage) {
        log.info("Sending message='{}' to topic='{}'", balanceUpdateMessage, topicName);
        walletUpdateBalanceKafkaTemplate.send(topicName,
                                              uuid,
                                              balanceUpdateMessage);
    }
}
