package ea.slartibartfast.paymentservice.domain.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class PaymentEventVo {

    private LocalDateTime createdAt;
    private String checkoutId;
    private List<PaymentOrderVo> orders;
    private String buyerAccount;
    private String sellerAccount;
    private String creditCardToken;
    private boolean paymentCompleted;
}
