package ea.slartibartfast.walletservice.domain.repository;

import ea.slartibartfast.walletservice.domain.model.BalanceTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceTransactionRepository extends JpaRepository<BalanceTransaction, Long> {
}
