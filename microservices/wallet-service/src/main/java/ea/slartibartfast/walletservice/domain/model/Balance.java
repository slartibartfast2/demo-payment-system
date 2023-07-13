package ea.slartibartfast.walletservice.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "balances")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Balance {

    @Id
    @GeneratedValue(generator = "idSeqGen")
    @SequenceGenerator(name = "idSeqGen", sequenceName = "balances_id_seq", allocationSize = 3)
    @Column(name = "id", nullable = false)
    private Long balanceId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "account", nullable = false)
    private String account;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "currency", nullable = false)
    private String currency;
}
