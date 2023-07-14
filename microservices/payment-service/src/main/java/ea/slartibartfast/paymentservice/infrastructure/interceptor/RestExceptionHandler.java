package ea.slartibartfast.paymentservice.infrastructure.interceptor;

import ea.slartibartfast.paymentservice.domain.exception.BusinessException;
import ea.slartibartfast.paymentservice.infrastructure.exception.AccountNotFoundException;
import ea.slartibartfast.paymentservice.infrastructure.exception.BadRequestException;
import ea.slartibartfast.paymentservice.infrastructure.exception.WalletServiceNotAvailableException;
import ea.slartibartfast.paymentservice.infrastructure.rest.controller.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Clock;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    private static final String FAILURE_STATUS = "failure";

    @ExceptionHandler({BusinessException.class, AccountNotFoundException.class, WalletServiceNotAvailableException.class})
    public ResponseEntity<Response> handleBusinessException(Exception ex) {
        Response response = new Response();
        response.setErrorMessage(ex.getMessage());
        response.setStatus(FAILURE_STATUS);
        response.setSystemTime(Clock.systemUTC().millis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<Response> handleNotAcceptableHttpMediaTypeException(HttpMediaTypeNotAcceptableException ex) {
        Response response = new Response();
        response.setErrorMessage(ex.getMessage());
        response.setStatus(FAILURE_STATUS);
        response.setSystemTime(Clock.systemUTC().millis());
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Response> handleBadRequestException() {
        Response response = new Response();
        response.setErrorMessage("Bad Request");
        response.setStatus(FAILURE_STATUS);
        response.setSystemTime(Clock.systemUTC().millis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);

        Response response = new Response();
        response.setErrorMessage(ex.getMessage());
        response.setStatus(FAILURE_STATUS);
        response.setSystemTime(Clock.systemUTC().millis());
        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}