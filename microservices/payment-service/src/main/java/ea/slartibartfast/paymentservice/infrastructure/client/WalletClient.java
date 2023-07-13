package ea.slartibartfast.paymentservice.infrastructure.client;

import ea.slartibartfast.paymentservice.infrastructure.client.request.UpdateBalanceRequest;
import ea.slartibartfast.paymentservice.infrastructure.client.response.BalanceResponse;

public interface WalletClient {
    BalanceResponse update(String apiKey, String correlationId, UpdateBalanceRequest updateBalanceRequest);
}