package io.huna.buckpal.account.application.port.out;

import io.huna.buckpal.account.domain.Account;

public interface UpdateAccountStatePort {

    void updateActivities(Account account);
}