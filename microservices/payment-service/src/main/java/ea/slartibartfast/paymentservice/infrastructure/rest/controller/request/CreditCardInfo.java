package ea.slartibartfast.paymentservice.infrastructure.rest.controller.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreditCardInfo {

    private String cardNumber;
    private String cardHolderName;
    private String cardToken;
    private boolean saveAsNewCard;
}
