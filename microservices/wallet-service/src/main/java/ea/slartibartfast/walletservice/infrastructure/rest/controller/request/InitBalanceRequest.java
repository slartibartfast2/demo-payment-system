package ea.slartibartfast.walletservice.infrastructure.rest.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InitBalanceRequest {

    @NotBlank
    private String account;

    @NotBlank
    @Size(min = 3, max = 3, message = "currency_iso must be 3 characters long")
    private String currency;
}
