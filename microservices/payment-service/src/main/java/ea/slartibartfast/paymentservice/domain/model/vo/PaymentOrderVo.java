package ea.slartibartfast.paymentservice.domain.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class PaymentOrderVo {

    private LocalDateTime createdAt;
    private String buyerAccount;
    private String sellerAccount;
    private BigDecimal amount;
    private String currency;
    private String paymentOrderStatus;
    private boolean ledgerUpdated;
    private boolean walletUpdated;
}
