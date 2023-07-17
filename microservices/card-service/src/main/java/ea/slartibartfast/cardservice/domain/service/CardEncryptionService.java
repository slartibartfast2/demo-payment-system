package ea.slartibartfast.cardservice.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Slf4j
@Service
public class CardEncryptionService {

    public String encrypt(String cardNumber) {
        var encoded = "";
        try {
            encoded = Base64.getEncoder().encodeToString(cardNumber.getBytes());
            log.info("Card number encrypted, wait for 1s.");
            Thread.sleep(1000);
        } catch (InterruptedException ie) {
            log.warn("interrupted!");
        }
        return encoded;
    }

    public String decrypt(String encryptedCardNumber) {
        var cardNumber = "";
        try {
            var cardNumberBytes = Base64.getDecoder().decode(encryptedCardNumber);
            cardNumber = new String(cardNumberBytes);
            log.info("Card number decrypted, wait for 1s.");
            Thread.sleep(1000);
        } catch (InterruptedException ie) {
            log.warn("interrupted!");
        }

        return cardNumber;
    }
}
