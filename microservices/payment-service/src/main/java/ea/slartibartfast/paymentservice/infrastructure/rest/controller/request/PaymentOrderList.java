package ea.slartibartfast.paymentservice.infrastructure.rest.controller.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PaymentOrderList {

    @Valid
    @NotEmpty
    private List<PaymentOrder> paymentOrders;
}
