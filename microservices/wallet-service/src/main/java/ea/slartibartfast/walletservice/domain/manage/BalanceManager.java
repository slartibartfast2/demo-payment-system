package ea.slartibartfast.walletservice.domain.manage;

import ea.slartibartfast.walletservice.domain.service.WalletService;
import ea.slartibartfast.walletservice.infrastructure.rest.controller.request.InitBalanceRequest;
import ea.slartibartfast.walletservice.infrastructure.rest.controller.response.BalanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BalanceManager {

    private final WalletService balanceService;

    public BalanceResponse initBalance(InitBalanceRequest initBalanceRequest) {
        var balanceVo = balanceService.initBalance(initBalanceRequest);
        return new BalanceResponse(balanceVo);
    }

    public BalanceResponse retrieveBalance(String account) {
        var balanceVo = balanceService.retrieveBalance(account);
        return new BalanceResponse(balanceVo);
    }
}
