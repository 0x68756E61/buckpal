package io.huna.buckpal.account.application.port.out;

import io.huna.buckpal.account.domain.Account;

public interface AccountLock {

    void lockAccount(Account.AccountId accountId);

    void releaseAccount(Account.AccountId accountId);
}
