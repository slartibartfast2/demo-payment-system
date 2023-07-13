package ea.slartibartfast.paymentservice.infrastructure.rest.controller.response;

import ea.slartibartfast.paymentservice.domain.model.vo.PaymentEventVo;

public record CreatePaymentResponse(PaymentEventVo paymentEventVo) {
}
