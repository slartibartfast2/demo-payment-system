package ea.slartibartfast.cardservice.domain.model.vo;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CardVo {

    private String cardNumber;
    private String cardHolderName;
    private String cardToken;
}
