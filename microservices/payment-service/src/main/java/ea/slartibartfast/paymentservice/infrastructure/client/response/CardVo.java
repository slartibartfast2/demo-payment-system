package ea.slartibartfast.paymentservice.infrastructure.client.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CardVo {

    private String cardNumber;
    private String cardHolderName;
    private String cardToken;
}
