package ea.slartibartfast.paymentservice.infrastructure.client;

import ea.slartibartfast.paymentservice.infrastructure.client.request.CreateCardRequest;
import ea.slartibartfast.paymentservice.infrastructure.client.response.CreateCardResponse;
import ea.slartibartfast.paymentservice.infrastructure.client.response.RetrieveCardResponse;
import ea.slartibartfast.paymentservice.infrastructure.config.CardCustomErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "card-service", configuration = CardCustomErrorDecoder.class)
public interface FeignCardClient extends CardClient {

    @PostMapping(value = "/api/v1/cards/")
    CreateCardResponse createCard(@RequestHeader("api-key") String apiKey,
                                  @RequestHeader("correlation-id") String correlationId,
                                  @RequestBody CreateCardRequest createCardRequest);

    @GetMapping(value = "/api/v1/cards({cardToken}")
    RetrieveCardResponse retrieveCard(@RequestHeader("api-key") String apiKey,
                                      @RequestHeader("correlation-id") String correlationId,
                                      @PathVariable("cardToken") String cardToken);
}
