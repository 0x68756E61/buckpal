package io.huna.buckpal.account.application.port.out;

import io.huna.buckpal.account.domain.Account;

import java.time.LocalDateTime;

public interface LoadAccountPort {

    Account loadAccount(Account.AccountId accountId, LocalDateTime baselineDate);
}