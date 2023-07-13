package ea.slartibartfast.paymentservice.domain.manager;

import ea.slartibartfast.paymentservice.domain.model.vo.PaymentEventVo;
import ea.slartibartfast.paymentservice.domain.service.PaymentEventService;
import ea.slartibartfast.paymentservice.domain.service.PaymentFacadeService;
import ea.slartibartfast.paymentservice.infrastructure.rest.controller.request.CreatePaymentRequest;
import ea.slartibartfast.paymentservice.infrastructure.rest.controller.response.CreatePaymentResponse;
import ea.slartibartfast.paymentservice.infrastructure.rest.controller.response.RetrievePaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class PaymentManager {

    private final PaymentFacadeService paymentFacadeService;

    public CreatePaymentResponse createPayment(CreatePaymentRequest createPaymentRequest) {
        PaymentEventVo paymentEventVo = paymentFacadeService.processPaymentEvent(createPaymentRequest);
        return new CreatePaymentResponse(paymentEventVo);
    }

    public RetrievePaymentResponse retrievePayment(String checkoutId) {
        PaymentEventVo paymentEventVo = paymentFacadeService.retrieveEvent(checkoutId);
        return new RetrievePaymentResponse(paymentEventVo);
    }
}
