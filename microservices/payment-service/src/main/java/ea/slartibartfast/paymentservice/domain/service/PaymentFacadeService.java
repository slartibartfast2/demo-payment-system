package ea.slartibartfast.paymentservice.domain.service;

import ea.slartibartfast.paymentservice.domain.model.vo.PaymentEventVo;
import ea.slartibartfast.paymentservice.infrastructure.rest.controller.request.CreatePaymentRequest;
import ea.slartibartfast.paymentservice.infrastructure.rest.controller.request.CreditCardInfo;
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
    private final CardService cardService;

    public PaymentEventVo processPaymentEvent(CreatePaymentRequest createPaymentRequest) {
        log.info("New payment event received, checkoutId: {}", createPaymentRequest.getCheckoutId());
        var cardToken = cardStrategy(createPaymentRequest.getCreditCardInfo());
        log.info("Card token: {}", cardToken);
        var paymentEventVo = paymentEventService.createEvent(createPaymentRequest, cardToken);
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

    private String cardStrategy(CreditCardInfo cardInfo) {
        if (cardInfo.getCardToken() == null && cardInfo.isSaveAsNewCard()) {
            var createCardResponse = cardService.createCard(cardInfo.getCardHolderName(), cardInfo.getCardNumber());
            return createCardResponse.cardToken();
        }

        return cardInfo.getCardToken();
    }

    public PaymentEventVo retrieveEvent(String checkoutId) {
        return paymentEventService.retrieveEvent(checkoutId);
    }
}
