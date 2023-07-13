package ea.slartibartfast.paymentservice.infrastructure.client;

import ea.slartibartfast.paymentservice.infrastructure.client.request.UpdateBalanceRequest;
import ea.slartibartfast.paymentservice.infrastructure.client.response.BalanceResponse;
import ea.slartibartfast.paymentservice.infrastructure.config.WalletCustomErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "wallet-service", configuration = WalletCustomErrorDecoder.class)
public interface FeignWalletClient extends WalletClient {

    @PostMapping(value = "/api/v1/balances")
    BalanceResponse update(@RequestHeader("api-key") String apiKey,
                           @RequestHeader("correlation-id") String correlationId,
                           @RequestBody UpdateBalanceRequest updateBalanceRequest);
}
