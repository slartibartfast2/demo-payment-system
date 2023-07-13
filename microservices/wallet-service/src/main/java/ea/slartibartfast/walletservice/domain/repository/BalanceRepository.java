package ea.slartibartfast.walletservice.domain.repository;

import ea.slartibartfast.walletservice.domain.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, Long> {

    Optional<Balance> findBalanceByAccount(String account);
}
