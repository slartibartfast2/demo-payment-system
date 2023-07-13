package ea.slartibartfast.paymentservice.infrastructure.exception;

public class WalletServiceNotAvailableException extends RuntimeException {
    public WalletServiceNotAvailableException(String message) {
        super(message);
    }
}
