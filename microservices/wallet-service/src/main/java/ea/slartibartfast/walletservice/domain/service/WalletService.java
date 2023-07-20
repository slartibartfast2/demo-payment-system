package ea.slartibartfast.walletservice.domain.service;

import ea.slartibartfast.walletservice.application.model.BalanceUpdateMessage;
import ea.slartibartfast.walletservice.domain.exception.BalanceLockException;
import ea.slartibartfast.walletservice.domain.exception.BusinessException;
import ea.slartibartfast.walletservice.domain.model.Balance;
import ea.slartibartfast.walletservice.domain.model.vo.BalanceVo;
import ea.slartibartfast.walletservice.domain.repository.BalanceRepository;
import ea.slartibartfast.walletservice.infrastructure.rest.controller.request.InitBalanceRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class WalletService {

    private final BalanceRepository balanceRepository;
    private final BalanceUpdateService balanceUpdateService;

    private final LockRegistry lockRegistry;

    private static final String BALANCE_LOCK_KEY_PREFIX = "balanceLock::";
    private static final BigDecimal INIT_BALANCE = new BigDecimal(1000);

    public BalanceVo retrieveBalance(String account) {
        return balanceRepository.findBalanceByAccount(account)
                                .map(this::mapBalanceToVo)
                                .orElseThrow(() -> new BusinessException("balance not found for this account"));
    }

    public void updateBalance(BalanceUpdateMessage balanceUpdateMessage) {
        var lock = lockRegistry.obtain(BALANCE_LOCK_KEY_PREFIX + balanceUpdateMessage.getAccount());
        try {
            var acquired = lock.tryLock();
            if (!acquired) {
                throw new BalanceLockException("cannot lock balance for this account!");
            }

            var balance = balanceUpdateService.updateBalance(balanceUpdateMessage);
            log.info("Balance updated for account {}, new balance is {}", balance.getAccount(), balance.getBalance());
        } finally {
            lock.unlock();
        }
    }

    public BalanceVo initBalance(InitBalanceRequest initBalanceRequest) {
        var balance = createBalance(initBalanceRequest.getAccount(), initBalanceRequest.getCurrency());
        balanceRepository.save(balance);
        return mapBalanceToVo(balance);
    }

    private Balance createBalance(String account, String currency) {
        return Balance.builder()
                      .balance(INIT_BALANCE)
                      .createdAt(LocalDateTime.now())
                      .account(account)
                      .currency(currency)
                      .build();
    }



    private BalanceVo mapBalanceToVo(Balance balance) {
        return BalanceVo.builder()
                        .balanceAmount(balance.getBalance())
                        .currency(balance.getCurrency())
                        .account(balance.getAccount())
                        .build();
    }
}
