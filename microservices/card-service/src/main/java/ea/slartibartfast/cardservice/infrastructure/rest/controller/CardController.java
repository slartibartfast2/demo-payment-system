package ea.slartibartfast.cardservice.infrastructure.rest.controller;

import ea.slartibartfast.cardservice.domain.manager.CardManager;
import ea.slartibartfast.cardservice.infrastructure.rest.controller.response.RetrieveCardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Payment System", description = "Payment processing APIs")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CardController {

    private final CardManager cardManager;

    @Operation(
            summary = "Retrieve card by yoken",
            description = "Get a Card object by specifying its token value.",
            tags = { "cards", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = RetrieveCardResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping(value = "/cards/{cardToken}", produces = "application/json")
    public RetrieveCardResponse retrieve(@PathVariable("cardToken") String cardToken) {
        return cardManager.retrieveCard(cardToken);
    }
}
