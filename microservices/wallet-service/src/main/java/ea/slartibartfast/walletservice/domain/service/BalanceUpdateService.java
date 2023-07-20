package ea.slartibartfast.walletservice.domain.service;

import ea.slartibartfast.walletservice.application.model.BalanceUpdateMessage;
import ea.slartibartfast.walletservice.domain.exception.BusinessException;
import ea.slartibartfast.walletservice.domain.model.Balance;
import ea.slartibartfast.walletservice.domain.model.BalanceTransaction;
import ea.slartibartfast.walletservice.domain.model.TransactionDirection;
import ea.slartibartfast.walletservice.domain.repository.BalanceRepository;
import ea.slartibartfast.walletservice.domain.repository.BalanceTransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class BalanceUpdateService {

    private final BalanceRepository balanceRepository;
    private final BalanceTransactionRepository balanceTransactionRepository;

    @Transactional
    public Balance updateBalance(BalanceUpdateMessage balanceUpdateMessage) {
        var balanceTransaction = createBalanceTransaction(balanceUpdateMessage);
        balanceTransactionRepository.save(balanceTransaction);

        var balance = balanceRepository.findBalanceByAccount(balanceUpdateMessage.getAccount())
                                       .orElseThrow(() -> new BusinessException(
                                               "balance not found for this account"));
        balance.setBalance(calculateNewBalance(balance, balanceUpdateMessage));
        balance.setUpdatedAt(LocalDateTime.now());
        return balanceRepository.save(balance);
    }

    private BigDecimal calculateNewBalance(Balance balance, BalanceUpdateMessage balanceUpdateMessage) {
        if (balanceUpdateMessage.getDirection().name().equals(TransactionDirection.DEBIT.name())) {
            return balance.getBalance().add(balanceUpdateMessage.getTransactionAmount());
        } else {
            return balance.getBalance().subtract(balanceUpdateMessage.getTransactionAmount());
        }
    }

    private BalanceTransaction createBalanceTransaction(BalanceUpdateMessage balanceUpdateMessage) {
        return BalanceTransaction.builder()
                                 .amount(balanceUpdateMessage.getTransactionAmount())
                                 .account(balanceUpdateMessage.getAccount())
                                 .currency(balanceUpdateMessage.getCurrency())
                                 .direction(TransactionDirection.valueOf(balanceUpdateMessage.getDirection().name()))
                                 .createdAt(LocalDateTime.now())
                                 .build();
    }
}
