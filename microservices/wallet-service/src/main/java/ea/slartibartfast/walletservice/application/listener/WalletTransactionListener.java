package ea.slartibartfast.walletservice.application.listener;

import ea.slartibartfast.walletservice.application.model.BalanceUpdateMessage;

public interface WalletTransactionListener {

    void processBalanceUpdate(BalanceUpdateMessage payload,
                              Integer partition,
                              Long offset,
                              String key);
}
