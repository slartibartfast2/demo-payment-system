package ea.slartibartfast.cardservice.domain.repository;

import ea.slartibartfast.cardservice.domain.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findCardByCardToken(String cardToken);
}
