package ea.slartibartfast.paymentservice.infrastructure.exception;

public class CardServiceNotAvailableException extends RuntimeException {
    public CardServiceNotAvailableException(String message) {
        super(message);
    }
}
