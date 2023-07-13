package ea.slartibartfast.paymentservice.infrastructure.rest.controller.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreatePaymentRequest {

    @NotBlank
    private String buyerAccount;

    @NotBlank
    private String checkoutId;

    @Valid
    @NotNull
    private CreditCardInfo creditCardInfo;

    @Valid
    @NotNull
    private PaymentOrderList paymentOrderList;
}
