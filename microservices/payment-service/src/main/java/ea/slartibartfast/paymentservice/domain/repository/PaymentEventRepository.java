package ea.slartibartfast.paymentservice.domain.repository;

import ea.slartibartfast.paymentservice.domain.model.PaymentEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentEventRepository extends JpaRepository<PaymentEvent, Long> {

    @Query("SELECT pe FROM PaymentEvent pe WHERE pe.checkoutId = ?1")
    Optional<PaymentEvent> findPaymentEventByCheckoutId(String checkoutId);
}
