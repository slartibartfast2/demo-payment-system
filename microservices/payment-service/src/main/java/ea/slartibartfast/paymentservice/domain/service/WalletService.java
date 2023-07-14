package ea.slartibartfast.paymentservice.domain.service;

import ea.slartibartfast.paymentservice.infrastructure.client.WalletClient;
import ea.slartibartfast.paymentservice.infrastructure.client.request.TransactionDirection;
import ea.slartibartfast.paymentservice.infrastructure.client.request.UpdateBalanceRequest;
import ea.slartibartfast.paymentservice.infrastructure.client.response.BalanceResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
@Service
public class WalletService {

    private final WalletClient walletClient;

    private static final String WALLET_TOKEN = "wallet-token";
    private static final String CORRELATION_ID_NAME = "correlation-id";

    @Retry(name = "walletService")
    @CircuitBreaker(name = "walletService")
    public BalanceResponse updateBalance(String account, BigDecimal amount, String currency) {
        var updateBalanceRequest = UpdateBalanceRequest.builder()
                                                       .direction(TransactionDirection.DEBIT)
                                                       .transactionAmount(amount)
                                                       .currency(currency)
                                                       .account(account)
                                                       .build();

        return walletClient.update(WALLET_TOKEN, MDC.get(CORRELATION_ID_NAME), updateBalanceRequest);
    }
}