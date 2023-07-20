package ea.slartibartfast.paymentservice.domain.service;

import ea.slartibartfast.paymentservice.domain.exception.BusinessException;
import ea.slartibartfast.paymentservice.domain.model.PaymentEvent;
import ea.slartibartfast.paymentservice.domain.model.PaymentOrder;
import ea.slartibartfast.paymentservice.domain.model.PaymentOrderStatus;
import ea.slartibartfast.paymentservice.domain.model.vo.PaymentEventVo;
import ea.slartibartfast.paymentservice.domain.model.vo.PaymentOrderVo;
import ea.slartibartfast.paymentservice.domain.repository.PaymentEventRepository;
import ea.slartibartfast.paymentservice.infrastructure.rest.controller.request.CreatePaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PaymentEventService {

    private final PaymentEventRepository paymentEventRepository;

    public PaymentEventVo createEvent(CreatePaymentRequest request, String cardToken) {
        var paymentEvent = createPaymentEventEntity(request, cardToken);
        paymentEventRepository.save(paymentEvent);

        return mapPaymentEventToVo(paymentEvent);
    }

    public PaymentEventVo retrieveEvent(String checkoutId) {
        return paymentEventRepository.findPaymentEventByCheckoutId(checkoutId)
                                     .map(this::mapPaymentEventToVo)
                                     .orElseThrow(() -> new BusinessException("Payment event not found"));
    }

    private List<PaymentOrder> createPaymentOrderListEntity(PaymentEvent paymentEvent, CreatePaymentRequest request) {
        return request.getPaymentOrderList()
                      .getPaymentOrders()
                      .stream()
                      .map(paymentOrder -> PaymentOrder.builder()
                                                       .buyerAccount(request.getBuyerAccount())
                                                       .sellerAccount(paymentOrder.getMerchantAccount())
                                                       .currency(paymentOrder.getCurrency())
                                                       .amount(paymentOrder.getAmount())
                                                       .paymentOrderStatus(PaymentOrderStatus.INITIALIZED)
                                                       .paymentEvent(paymentEvent)
                                                       .build()).toList();
    }

    private PaymentEvent createPaymentEventEntity(CreatePaymentRequest request, String cardToken) {
        var paymentEvent = PaymentEvent.builder()
                                       .createdAt(LocalDateTime.now())
                                       .checkoutId(request.getCheckoutId())
                                       .buyerAccount(request.getBuyerAccount())
                                       .creditCardToken(cardToken)
                                       .build();

        paymentEvent.setOrders(createPaymentOrderListEntity(paymentEvent, request));

        return paymentEvent;
    }

    private PaymentEventVo mapPaymentEventToVo(PaymentEvent paymentEvent) {
        List<PaymentOrderVo> paymentOrderVoList = mapPaymentOrdersToVo(paymentEvent.getOrders());

        return PaymentEventVo.builder()
                             .paymentCompleted(paymentEvent.isPaymentCompleted())
                             .createdAt(paymentEvent.getCreatedAt())
                             .creditCardToken(paymentEvent.getCreditCardToken())
                             .buyerAccount(paymentEvent.getBuyerAccount())
                             .checkoutId(paymentEvent.getCheckoutId())
                             .orders(paymentOrderVoList)
                             .build();
    }

    private List<PaymentOrderVo> mapPaymentOrdersToVo(List<PaymentOrder> orders) {
        return orders.stream()
                     .map(order -> PaymentOrderVo.builder()
                                                 .ledgerUpdated(order.isLedgerUpdated())
                                                 .walletUpdated(order.isWalletUpdated())
                                                 .buyerAccount(order.getBuyerAccount())
                                                 .sellerAccount(order.getSellerAccount())
                                                 .createdAt(order.getCreatedAt())
                                                 .paymentOrderStatus(order.getPaymentOrderStatus().name())
                                                 .currency(order.getCurrency())
                                                 .amount(order.getAmount())
                                                 .build())
                     .toList();
    }
}
