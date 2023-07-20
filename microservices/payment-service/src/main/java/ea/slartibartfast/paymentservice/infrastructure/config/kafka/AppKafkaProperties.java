package ea.slartibartfast.paymentservice.infrastructure.config.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "app.kafka")
public record AppKafkaProperties(
        DeadLetter deadletter,
        Backoff backoff) {
}

record DeadLetter(
        Duration retention,
        String suffix) {
}

record Backoff(
        Duration initialInterval,
        Duration maxInterval,
        int maxRetries,
        double multiplier) {
}