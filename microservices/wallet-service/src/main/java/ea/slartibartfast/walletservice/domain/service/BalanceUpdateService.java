package ea.slartibartfast.walletservice.domain.service;

import ea.slartibartfast.walletservice.domain.exception.BusinessException;
import ea.slartibartfast.walletservice.domain.model.Balance;
import ea.slartibartfast.walletservice.domain.model.BalanceTransaction;
import ea.slartibartfast.walletservice.domain.model.TransactionDirection;
import ea.slartibartfast.walletservice.domain.repository.BalanceRepository;
import ea.slartibartfast.walletservice.domain.repository.BalanceTransactionRepository;
import ea.slartibartfast.walletservice.infrastructure.rest.controller.request.UpdateBalanceRequest;
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
    public Balance updateBalance(UpdateBalanceRequest updateBalanceRequest) {
        var balanceTransaction = createBalanceTransaction(updateBalanceRequest);
        balanceTransactionRepository.save(balanceTransaction);

        var balance = balanceRepository.findBalanceByAccount(updateBalanceRequest.getAccount())
                                       .orElseThrow(() -> new BusinessException(
                                               "balance not found for this account"));
        balance.setBalance(calculateNewBalance(balance, updateBalanceRequest));
        balance.setUpdatedAt(LocalDateTime.now());
        return balanceRepository.save(balance);
    }

    private BigDecimal calculateNewBalance(Balance balance, UpdateBalanceRequest updateBalanceRequest) {
        if (updateBalanceRequest.getDirection().name().equals(TransactionDirection.DEBIT.name())) {
            return balance.getBalance().add(updateBalanceRequest.getTransactionAmount());
        } else {
            return balance.getBalance().subtract(updateBalanceRequest.getTransactionAmount());
        }
    }

    private BalanceTransaction createBalanceTransaction(UpdateBalanceRequest updateBalanceRequest) {
        return BalanceTransaction.builder()
                                 .amount(updateBalanceRequest.getTransactionAmount())
                                 .account(updateBalanceRequest.getAccount())
                                 .currency(updateBalanceRequest.getCurrency())
                                 .direction(TransactionDirection.valueOf(updateBalanceRequest.getDirection().name()))
                                 .createdAt(LocalDateTime.now())
                                 .build();
    }
}
