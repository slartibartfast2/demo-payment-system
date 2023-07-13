package ea.slartibartfast.paymentservice.infrastructure.rest.controller.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class PaymentOrder {

    @NotBlank
    private String paymentOrderId;

    @DecimalMin("0.0")
    @Digits(integer=30, fraction=8)
    private BigDecimal amount;

    @NotBlank
    @Size(min = 3, max = 3, message = "currency_iso must be 3 characters long")
    private String currency;

    @NotBlank
    private String merchantAccount;
}
