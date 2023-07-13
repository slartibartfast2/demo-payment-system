package ea.slartibartfast.walletservice.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "balance_transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BalanceTransaction {

    @Id
    @GeneratedValue(generator = "idSeqGen")
    @SequenceGenerator(name = "idSeqGen", sequenceName = "balance_transactions_id_seq", allocationSize = 3)
    @Column(name = "id", nullable = false)
    private Long balanceTransactionId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "account", nullable = false)
    private String account;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "direction", nullable = false)
    private TransactionDirection direction;
}
