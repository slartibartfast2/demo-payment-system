package ea.slartibartfast.paymentservice.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentOrder implements Serializable {

    @Id
    @GeneratedValue(generator = "idSeqGen")
    @SequenceGenerator(name = "idSeqGen", sequenceName = "payment_orders_id_seq", allocationSize = 3)
    @Column(name = "id", nullable = false)
    private Long paymentOrderId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="payment_event_id", nullable=false)
    private PaymentEvent paymentEvent;

    @Column(name = "buyer_account", nullable = false)
    private String buyerAccount;

    @Column(name = "seller_account", nullable = false)
    private String sellerAccount;

    @Column(name = "order_amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_order_status", nullable = false)
    private PaymentOrderStatus paymentOrderStatus;

    @Column(name = "ledger_updated")
    private boolean ledgerUpdated;

    @Column(name = "wallet_updated")
    private boolean walletUpdated;
}
