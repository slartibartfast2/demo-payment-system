package ea.slartibartfast.walletservice.infrastructure.config.kafka;

import ea.slartibartfast.walletservice.application.model.BalanceUpdateMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    private final KafkaProperties kafkaProperties;
    private final KafkaTemplate<String, BalanceUpdateMessage> kafkaTemplate;
    private final AppKafkaProperties appKafkaProperties;

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>(
                kafkaProperties.buildConsumerProperties()
        );
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonSerializer.TYPE_MAPPINGS, "balanceUpdateMessage:ea.slartibartfast.walletservice.application.model.BalanceUpdateMessage");
        return props;
    }

    @Bean
    public ConsumerFactory<String, BalanceUpdateMessage> walletTransactionConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BalanceUpdateMessage> walletKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, BalanceUpdateMessage>();
        factory.setConsumerFactory(walletTransactionConsumerFactory());
        factory.setCommonErrorHandler(errorHandler(appKafkaProperties, kafkaTemplate));
        return factory;
    }

    @Bean
    public DefaultErrorHandler errorHandler(
            AppKafkaProperties properties,
            KafkaTemplate<String, BalanceUpdateMessage> kafkaTemplate) {
        var recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,
                (cr, e) -> {
                    log.error("consumed record {} because this exception was thrown: {}", cr.toString(), ExceptionUtils.getRootCauseMessage(e));
                    return new TopicPartition(cr.topic() + properties.deadletter().suffix(), 0);
        });

        Backoff backoff = properties.backoff();
        var exponentialBackOff = new ExponentialBackOffWithMaxRetries(backoff.maxRetries());
        exponentialBackOff.setInitialInterval(backoff.initialInterval().toMillis());
        exponentialBackOff.setMultiplier(backoff.multiplier());
        exponentialBackOff.setMaxInterval(backoff.maxInterval().toMillis());

        var errorHandler = new DefaultErrorHandler(recoverer, exponentialBackOff);
        errorHandler.addRetryableExceptions(SocketTimeoutException.class, RuntimeException.class);
        errorHandler.addNotRetryableExceptions(NullPointerException.class);

        return errorHandler;
    }
}
