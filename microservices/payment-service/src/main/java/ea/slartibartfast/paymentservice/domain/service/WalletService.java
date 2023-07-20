package ea.slartibartfast.paymentservice.domain.service;

import ea.slartibartfast.paymentservice.application.model.BalanceUpdateMessage;
import ea.slartibartfast.paymentservice.application.model.TransactionDirection;
import ea.slartibartfast.paymentservice.infrastructure.kafka.producer.WalletProducer;
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

    private final WalletProducer walletProducer;

    private static final String CORRELATION_ID_NAME = "correlation-id";

    @Retry(name = "walletService")
    @CircuitBreaker(name = "walletService")
    public void updateBalance(String account, BigDecimal amount, String currency) {
        var requestUUID = MDC.get(CORRELATION_ID_NAME);
        var updateBalanceRequest = BalanceUpdateMessage.builder()
                                                       .requestUUID(requestUUID)
                                                       .direction(TransactionDirection.DEBIT)
                                                       .transactionAmount(amount)
                                                       .currency(currency)
                                                       .account(account)
                                                       .build();

        walletProducer.sendBalanceUpdate(requestUUID, updateBalanceRequest);

        log.info("New balance message sent to MQ for account {}", account);
    }
}