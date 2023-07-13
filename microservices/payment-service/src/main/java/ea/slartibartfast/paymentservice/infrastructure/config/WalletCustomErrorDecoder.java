package ea.slartibartfast.paymentservice.infrastructure.config;

import ea.slartibartfast.paymentservice.infrastructure.exception.AccountNotFoundException;
import ea.slartibartfast.paymentservice.infrastructure.exception.BadRequestException;
import ea.slartibartfast.paymentservice.infrastructure.exception.WalletServiceNotAvailableException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class WalletCustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()){
            case 400:
                return new BadRequestException();
            case 404:
                return new AccountNotFoundException("Product not found");
            case 503:
                return new WalletServiceNotAvailableException("Wallet Api is unavailable");
            default:
                return new Exception("Exception while updating wallet for balance");
        }
    }
}
