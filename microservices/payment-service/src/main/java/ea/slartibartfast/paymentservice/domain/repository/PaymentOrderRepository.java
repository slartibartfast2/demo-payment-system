package ea.slartibartfast.paymentservice.domain.repository;

import ea.slartibartfast.paymentservice.domain.model.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {
}
