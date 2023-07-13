package ea.slartibartfast.paymentservice.domain.service;

import ea.slartibartfast.paymentservice.domain.model.vo.PaymentEventVo;
import ea.slartibartfast.paymentservice.infrastructure.rest.controller.request.CreatePaymentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentFacadeService {

    private final PaymentEventService paymentEventService;
    private final MockPSPService pspService;
    private final WalletService walletService;

    public PaymentEventVo processPaymentEvent(CreatePaymentRequest createPaymentRequest) {
        log.info("New payment event received, checkoutId: {}", createPaymentRequest.getCheckoutId());
        var paymentEventVo = paymentEventService.createEvent(createPaymentRequest);
        log.info("Payment event and orders created on DB.");
        pspService.callPSP(paymentEventVo);

        paymentEventVo.getOrders()
                      .forEach(paymentOrder -> {
                          var response = walletService.updateBalance(paymentOrder.getSellerAccount(),
                                                                     paymentOrder.getAmount(),
                                                                     paymentOrder.getCurrency());
                          log.info("New balance for {} is {}",
                                   paymentOrder.getSellerAccount(),
                                   response.balanceVo().getBalanceAmount());
                      });

        log.info("Seller balances updated for payment orders");
        //TODO:: updateLedger
        log.info("Buyer and seller info inserted to ledger.");
        return paymentEventVo;
    }

    public PaymentEventVo retrieveEvent(String checkoutId) {
        return paymentEventService.retrieveEvent(checkoutId);
    }
}
