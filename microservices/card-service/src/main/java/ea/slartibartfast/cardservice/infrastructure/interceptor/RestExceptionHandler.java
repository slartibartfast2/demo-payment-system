package ea.slartibartfast.cardservice.infrastructure.interceptor;

import ea.slartibartfast.cardservice.domain.exception.BusinessException;
import ea.slartibartfast.cardservice.infrastructure.rest.controller.response.Response;
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

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Response> handleBusinessException(BusinessException ex) {
        Response response = new Response();
        response.setErrorMessage(ex.getMessage());
        response.setStatus("failure");
        response.setSystemTime(Clock.systemUTC().millis());
        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
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