package io.huna.buckpal.account.application.port.in;

import io.huna.buckpal.account.domain.Account;
import io.huna.buckpal.account.domain.Money;
import io.huna.buckpal.common.SelfValidating;
import lombok.NonNull;
import lombok.Value;

@Value
public class SendMoneyCommand extends SelfValidating<SendMoneyCommand> {

    @NonNull
    private final Account.AccountId sourceAccountId;

    @NonNull
    private final Account.AccountId targetAccountId;

    @NonNull
    private final Money money;

    public SendMoneyCommand(Account.AccountId sourceAccountId, Account.AccountId targetAccountId, Money money) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.money = money;
        this.validateSelf();
    }
}
