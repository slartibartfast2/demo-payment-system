package ea.slartibartfast.paymentservice.infrastructure.client.request;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCardRequest {
    private String cardNumber;
    private String cardHolderName;
}
