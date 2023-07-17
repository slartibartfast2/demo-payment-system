package ea.slartibartfast.paymentservice.infrastructure.client;

import ea.slartibartfast.paymentservice.infrastructure.client.request.CreateCardRequest;
import ea.slartibartfast.paymentservice.infrastructure.client.response.CreateCardResponse;
import ea.slartibartfast.paymentservice.infrastructure.client.response.RetrieveCardResponse;

public interface CardClient {

    CreateCardResponse createCard(String apiKey, String correlationId, CreateCardRequest createCardRequest);

    RetrieveCardResponse retrieveCard(String apiKey, String correlationId, String cardToken);

}
