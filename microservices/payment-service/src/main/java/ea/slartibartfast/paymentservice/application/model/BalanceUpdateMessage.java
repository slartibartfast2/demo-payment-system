package ea.slartibartfast.paymentservice.application.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BalanceUpdateMessage {
    private String requestUUID;
    private String account;
    private BigDecimal transactionAmount;
    private String currency;
    private TransactionDirection direction;
}
