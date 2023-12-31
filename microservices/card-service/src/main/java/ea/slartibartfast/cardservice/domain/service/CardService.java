package ea.slartibartfast.cardservice.domain.service;

import ea.slartibartfast.cardservice.domain.exception.BusinessException;
import ea.slartibartfast.cardservice.domain.model.Card;
import ea.slartibartfast.cardservice.domain.model.vo.CardVo;
import ea.slartibartfast.cardservice.domain.repository.CardRepository;
import ea.slartibartfast.cardservice.infrastructure.rest.controller.request.CreateCardRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class CardService {

    private final CardRepository cardRepository;
    private final CardEncryptionService cardEncryptionService;

    public CardVo retrieveCard(String cardToken) {
        return cardRepository.findCardByCardToken(cardToken)
                             .map(this::mapCardToVo)
                             .orElseThrow(() -> new BusinessException("Card not found!"));
    }

    public String createCard(CreateCardRequest request) {
        var card = createCardEntity(request);
        log.info("Card entity created");
        cardRepository.save(card);
        log.info("Card saved into DB");
        return card.getCardToken();
    }

    private Card createCardEntity(CreateCardRequest request) {
        var cardToken = UUID.nameUUIDFromBytes(request.getCardNumber().getBytes()).toString();
        log.info("New card token generated for card, {}", cardToken);
        return Card.builder()
                   .cardNumber(cardEncryptionService.encrypt(request.getCardNumber()))
                   .cardHolderName(request.getCardHolderName())
                   .createdAt(LocalDateTime.now())
                   .cardToken(cardToken)
                   .build();
    }

    private CardVo mapCardToVo(Card card) {
        var cardVo = CardVo.builder()
                     .cardHolderName(card.getCardHolderName())
                     .cardToken(card.getCardToken())
                     .cardNumber(cardEncryptionService.decrypt(card.getCardNumber()))
                     .build();
        log.info("Card entity converted to VO");
        return cardVo;
    }
}
