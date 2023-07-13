package ea.slartibartfast.walletservice.infrastructure.interceptor;

import ea.slartibartfast.walletservice.domain.exception.BalanceLockException;
import ea.slartibartfast.walletservice.domain.exception.BusinessException;
import ea.slartibartfast.walletservice.infrastructure.rest.controller.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Clock;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Response> handleBusinessException(BusinessException ex) {
        Response response = new Response();
        response.setErrorMessage("Business exception occurred!");
        response.setStatus("failure");
        response.setSystemTime(Clock.systemUTC().millis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BalanceLockException.class)
    public ResponseEntity<Response> handleBalanceLockException(BalanceLockException ex) {
        Response response = new Response();
        response.setErrorMessage(ex.getMessage());
        response.setStatus("failure");
        response.setSystemTime(Clock.systemUTC().millis());
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<Response> handleNotAcceptableHttpMediaTypeException(HttpMediaTypeNotAcceptableException ex) {
        Response response = new Response();
        response.setErrorMessage(ex.getMessage());
        response.setStatus("failure");
        response.setSystemTime(Clock.systemUTC().millis());
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);

        Response response = new Response();
        response.setErrorMessage(ex.getMessage());
        response.setStatus("failure");
        response.setSystemTime(Clock.systemUTC().millis());
        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}