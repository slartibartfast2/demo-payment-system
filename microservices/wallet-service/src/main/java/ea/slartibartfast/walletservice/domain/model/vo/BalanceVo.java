package ea.slartibartfast.walletservice.domain.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class BalanceVo {
    private String account;
    private BigDecimal balanceAmount;
    private String currency;
}
