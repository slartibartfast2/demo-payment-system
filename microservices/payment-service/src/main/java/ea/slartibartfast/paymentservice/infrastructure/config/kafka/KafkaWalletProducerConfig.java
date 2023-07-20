package ea.slartibartfast.paymentservice.infrastructure.config.kafka;

import ea.slartibartfast.paymentservice.application.model.BalanceUpdateMessage;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class KafkaWalletProducerConfig {

    private final KafkaProperties kafkaProperties;

    @Value("${app.kafka.wallet.topic.default-name}")
    private String defaultTopicName;

    @Bean
    public Map<String, Object> walletProducerConfigs() {
        Map<String, Object> props = new HashMap<>(
                kafkaProperties.buildConsumerProperties()
        );
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(JsonSerializer.TYPE_MAPPINGS, "balanceUpdateMessage:ea.slartibartfast.paymentservice.application.model.BalanceUpdateMessage");
        return props;
    }

    @Bean
    ProducerFactory<String, BalanceUpdateMessage> walletProducerFactory() {
        return new DefaultKafkaProducerFactory<>(walletProducerConfigs());
    }

    @Bean
    public KafkaTemplate<String, BalanceUpdateMessage> walletKafkaTemplate() {
        KafkaTemplate<String, BalanceUpdateMessage> kafkaTemplate = new KafkaTemplate<>(walletProducerFactory());
        kafkaTemplate.setMessageConverter(new StringJsonMessageConverter());
        kafkaTemplate.setDefaultTopic(defaultTopicName);
        return kafkaTemplate;
    }
}
