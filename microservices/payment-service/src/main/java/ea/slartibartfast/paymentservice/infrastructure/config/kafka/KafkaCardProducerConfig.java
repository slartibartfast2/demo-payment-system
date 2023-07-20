package ea.slartibartfast.paymentservice.infrastructure.config.kafka;

import ea.slartibartfast.paymentservice.application.model.CreateCardMessage;
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
public class KafkaCardProducerConfig {

    private final KafkaProperties kafkaProperties;

    @Value("${app.kafka.card.topic.default-name}")
    private String defaultTopicName;

    @Bean
    public Map<String, Object> cardProducerConfigs() {
        Map<String, Object> props = new HashMap<>(
                kafkaProperties.buildConsumerProperties()
        );
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(JsonSerializer.TYPE_MAPPINGS, "createCardMessage:ea.slartibartfast.paymentservice.application.model.CreateCardMessage");
        return props;
    }

    @Bean
    ProducerFactory<String, CreateCardMessage> cardProducerFactory() {
        return new DefaultKafkaProducerFactory<>(cardProducerConfigs());
    }

    @Bean
    public KafkaTemplate<String, CreateCardMessage> cardKafkaTemplate() {
        KafkaTemplate<String, CreateCardMessage> kafkaTemplate = new KafkaTemplate<>(cardProducerFactory());
        kafkaTemplate.setMessageConverter(new StringJsonMessageConverter());
        kafkaTemplate.setDefaultTopic(defaultTopicName);
        return kafkaTemplate;
    }
}
