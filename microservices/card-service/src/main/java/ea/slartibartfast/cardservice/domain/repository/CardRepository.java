package ea.slartibartfast.cardservice.domain.repository;

import ea.slartibartfast.cardservice.domain.model.Card;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface CardRepository extends ReactiveCrudRepository<Card, Long> {

    Mono<Card> findCardByCardToken(String cardToken);
}
