package ea.slartibartfast.walletservice.infrastructure.kafka.consumer;

import ea.slartibartfast.walletservice.application.listener.WalletTransactionListener;
import ea.slartibartfast.walletservice.application.model.BalanceUpdateMessage;
import ea.slartibartfast.walletservice.domain.service.WalletService;
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
public class KafkaWalletTransactionListener implements WalletTransactionListener {

    private final WalletService walletService;

    @KafkaListener(groupId = "wallet-trx", topics = "${app.kafka.topic.default-name}", containerFactory = "walletKafkaListenerContainerFactory")
    public void processBalanceUpdate(
            @Payload BalanceUpdateMessage payload,
            @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
            @Header(KafkaHeaders.OFFSET) Long offset,
            @Header(KafkaHeaders.RECEIVED_KEY) String messageKey) {
        log.info("Received Message: {} from partition: {}, message key is: {}", payload, partition, messageKey);

        walletService.updateBalance(payload);

        log.info("Balance update message consumed");
    }

    @KafkaListener(groupId = "user-trx-batch", topics = "${app.kafka.topic.default-name}${app.kafka.deadletter.suffix}")
    public void processDLT(String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.warn("received DLT message : [{}] from topic : [{}] at [{}]", message, topic, LocalDateTime.now());
    }
}