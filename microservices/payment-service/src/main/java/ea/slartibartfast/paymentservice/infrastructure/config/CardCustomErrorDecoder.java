package ea.slartibartfast.paymentservice.infrastructure.config;

import ea.slartibartfast.paymentservice.infrastructure.exception.*;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CardCustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()){
            case 400:
                return new BadRequestException();
            case 404:
                return new CardNotFoundException("Card not found");
            case 422:
                return new CardServiceNotAvailableException("Card Api is unavailable");
            case 503:
                return new CardServiceNotAvailableException("Card Api is unavailable");
            default:
                return new Exception("Exception while creating card");
        }
    }
}
