package ea.slartibartfast.paymentservice.infrastructure.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${app.kafka.wallet.topic.default-name}")
    private String walletTopicName;

    @Value("${app.kafka.card.topic.default-name}")
    private String cardTopicName;

    @Bean
    public NewTopic walletTopic() {
        return TopicBuilder.name(walletTopicName)
                .partitions(4)
                .replicas(2)
                .build();
    }

    @Bean
    public NewTopic cardTopic() {
        return TopicBuilder.name(cardTopicName)
                           .partitions(4)
                           .replicas(2)
                           .build();
    }

    @Bean
    public NewTopic walletDeadLetterTopic(AppKafkaProperties properties) {
        return TopicBuilder.name(walletTopicName + properties.deadletter().suffix())
                .partitions(1)
                .config(TopicConfig.RETENTION_MS_CONFIG, "" + properties.deadletter().retention().toMillis())
                .build();
    }

    @Bean
    public NewTopic cardDeadLetterTopic(AppKafkaProperties properties) {
        return TopicBuilder.name(cardTopicName + properties.deadletter().suffix())
                           .partitions(1)
                           .config(TopicConfig.RETENTION_MS_CONFIG, "" + properties.deadletter().retention().toMillis())
                           .build();
    }
}
