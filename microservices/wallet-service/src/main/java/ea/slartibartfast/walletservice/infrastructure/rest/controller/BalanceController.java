package ea.slartibartfast.walletservice.infrastructure.rest.controller;

import ea.slartibartfast.walletservice.domain.manage.BalanceManager;
import ea.slartibartfast.walletservice.infrastructure.rest.controller.request.InitBalanceRequest;
import ea.slartibartfast.walletservice.infrastructure.rest.controller.request.UpdateBalanceRequest;
import ea.slartibartfast.walletservice.infrastructure.rest.controller.response.BalanceResponse;
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
public class BalanceController {

    private final BalanceManager balanceManager;

    @Operation(summary = "Init account balance", tags = { "balance", "patch" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = BalanceResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping(value = "/balances/init", consumes = "application/json", produces = "application/json")
    public BalanceResponse init(@Valid @RequestBody InitBalanceRequest initBalanceRequest) {
        return balanceManager.initBalance(initBalanceRequest);
    }

    @Operation(summary = "Update account balance", tags = { "balance", "patch" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = BalanceResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping(value = "/balances", consumes = "application/json", produces = "application/json")
    public BalanceResponse update(@Valid @RequestBody UpdateBalanceRequest updateBalanceRequest) {
        return balanceManager.updateBalance(updateBalanceRequest);
    }

    @Operation(
            summary = "Retrieve account balance",
            description = "Get a Balance object by specifying its account.",
            tags = { "balance", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = BalanceResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping(value = "/balances/{account}", produces = "application/json")
    public BalanceResponse retrieve(@PathVariable("account") String account) {
        return balanceManager.retrieveBalance(account);
    }
}
