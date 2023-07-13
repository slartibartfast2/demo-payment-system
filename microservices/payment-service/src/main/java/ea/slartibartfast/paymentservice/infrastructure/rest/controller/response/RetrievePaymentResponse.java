package ea.slartibartfast.paymentservice.infrastructure.rest.controller.response;

import ea.slartibartfast.paymentservice.domain.model.vo.PaymentEventVo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public record RetrievePaymentResponse(PaymentEventVo paymentEventVo) {
}