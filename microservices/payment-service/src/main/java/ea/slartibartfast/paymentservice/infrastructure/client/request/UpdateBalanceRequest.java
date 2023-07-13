package ea.slartibartfast.paymentservice.infrastructure.client.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateBalanceRequest {
    @NotBlank
    private String account;

    @DecimalMin("0.0")
    @Digits(integer=30, fraction=8)
    private BigDecimal transactionAmount;

    @NotBlank
    @Size(min = 3, max = 3, message = "currency_iso must be 3 characters long")
    private String currency;

    @Schema(type = "string", allowableValues = {"DEBIT", "CREDIT"}, required = true)
    @NotNull
    private TransactionDirection direction;
}
