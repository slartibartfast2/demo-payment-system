package ea.slartibartfast.cardservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {

    @Id
    @Column(value = "id")
    private Long paymentEventId;

    @Column(value = "created_at")
    private LocalDateTime createdAt;

    @Column(value = "card_number")
    private String cardNumber;

    @Column(value = "card_holder_name")
    private String cardHolderName;

    @Column(value = "card_token")
    private String cardToken;
}
