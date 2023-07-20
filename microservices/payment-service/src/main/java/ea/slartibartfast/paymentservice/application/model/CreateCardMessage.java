package ea.slartibartfast.paymentservice.application.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateCardMessage {
    private String requestUUID;
    private String cardNumber;
    private String cardHolderName;
}
