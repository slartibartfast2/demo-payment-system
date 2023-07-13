package ea.slartibartfast.paymentservice.infrastructure.rest.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreditCardInfo {

    @NotBlank
    private String cardNumber;

    @NotBlank
    private String cardHolderName;
}
