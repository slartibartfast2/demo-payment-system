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
public class BalanceTransactionVo {
    private String account;
    private BigDecimal amount;
    private String currency;
}
