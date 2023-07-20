package ea.slartibartfast.cardservice.application.listener;

import ea.slartibartfast.cardservice.application.model.CreateCardMessage;

public interface CardTransactionListener {

    void processCardCreate(CreateCardMessage payload,
                           Integer partition,
                           Long offset,
                           String key);
}
