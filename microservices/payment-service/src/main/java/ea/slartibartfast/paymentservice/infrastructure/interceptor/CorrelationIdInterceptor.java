package ea.slartibartfast.paymentservice.infrastructure.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class CorrelationIdInterceptor implements HandlerInterceptor {

    private static final String CORRELATION_ID_NAME = "correlation-id";

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        final String correlationId = getOrGenerateCorrelationId(request);
        MDC.put(CORRELATION_ID_NAME, correlationId);
        log.info("New correlation id: {}", correlationId);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(final HttpServletRequest request,
                                final HttpServletResponse response,
                                Object handler,
                                Exception ex) {
        MDC.remove(CORRELATION_ID_NAME);
    }

    private String getOrGenerateCorrelationId(final HttpServletRequest request) {
        final String correlationId = request.getHeader(CORRELATION_ID_NAME);
        if (Optional.ofNullable(correlationId).isEmpty()) {
            return UUID.randomUUID().toString();
        }
        return correlationId;
    }
}
