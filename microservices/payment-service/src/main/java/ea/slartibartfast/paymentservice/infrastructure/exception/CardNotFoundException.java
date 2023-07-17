package ea.slartibartfast.paymentservice.infrastructure.exception;

public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException(String message) {
        super(message);
    }
}
