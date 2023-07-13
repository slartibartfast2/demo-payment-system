package ea.slartibartfast.paymentservice.domain.service;

import ea.slartibartfast.paymentservice.domain.model.vo.PaymentEventVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MockPSPService {

    public void callPSP(PaymentEventVo paymentEventVo) {
        log.info("PSP called and payment processed successfully, checkoutId: {}", paymentEventVo.getCheckoutId()) ;
    }
}
