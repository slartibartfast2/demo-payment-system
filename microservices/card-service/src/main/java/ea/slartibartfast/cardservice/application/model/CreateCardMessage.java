package ea.slartibartfast.cardservice.application.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateCardMessage {
    private String cardNumber;
    private String cardHolderName;
}
