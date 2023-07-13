package ea.slartibartfast.paymentservice.infrastructure.rest.controller;

import ea.slartibartfast.paymentservice.domain.manager.PaymentManager;
import ea.slartibartfast.paymentservice.infrastructure.rest.controller.request.CreatePaymentRequest;
import ea.slartibartfast.paymentservice.infrastructure.rest.controller.response.CreatePaymentResponse;
import ea.slartibartfast.paymentservice.infrastructure.rest.controller.response.RetrievePaymentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Payment System", description = "Payment processing APIs")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class PaymentController {

    private final PaymentManager paymentManager;

    @Operation(summary = "Create a new Payment", tags = { "tutorials", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = CreatePaymentResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping(value = "/payments", consumes = "application/json", produces = "application/json")
    public CreatePaymentResponse add(@Valid @RequestBody CreatePaymentRequest createPaymentRequest) {
        return paymentManager.createPayment(createPaymentRequest);
    }

    @Operation(
            summary = "Retrieve a Payment by Id",
            description = "Get a Payment object by specifying its id.",
            tags = { "payments", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = RetrievePaymentResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping(value = "/payments/{checkoutId}", produces = "application/json")
    public RetrievePaymentResponse retrieve(@PathVariable("checkoutId") String checkoutId) {
        return paymentManager.retrievePayment(checkoutId);
    }
}
