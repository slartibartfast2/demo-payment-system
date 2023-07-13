package ea.slartibartfast.paymentservice.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "payment_events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentEvent implements Serializable {

    @Id
    @GeneratedValue(generator = "idSeqGen")
    @SequenceGenerator(name = "idSeqGen", sequenceName = "payment_events_id_seq", allocationSize = 3)
    @Column(name = "id", nullable = false)
    private Long paymentEventId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "checkout_id", nullable = false)
    private String checkoutId;

    @OneToMany(mappedBy="paymentEvent", fetch = FetchType.EAGER,  cascade = CascadeType.ALL)
    private List<PaymentOrder> orders = new ArrayList<>();

    @Column(name = "buyer_account", nullable = false)
    private String buyerAccount;

    @Column(name = "credit_card_token", nullable = false)
    private String creditCardToken;

    @Column(name = "payment_completed", nullable = false)
    private boolean paymentCompleted;
}
