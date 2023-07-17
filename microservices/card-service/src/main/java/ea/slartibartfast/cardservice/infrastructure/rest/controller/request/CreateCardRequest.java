package ea.slartibartfast.cardservice.infrastructure.rest.controller.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateCardRequest {
    private String cardNumber;
    private String cardHolderName;
}
