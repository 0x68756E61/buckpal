package io.huna.buckpal.account.domain;

import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;

@Getter
public class Activity {

    private ActivityId id;

    @NonNull
    private final Account.AccountId ownerAccountId;

    @NonNull
    private final Account.AccountId sourceAccountId;

    @NonNull
    private final Account.AccountId targetAccountId;

    @NonNull
    private final LocalDateTime timestamp;

    @NonNull
    private final Money money;

    public Activity(
            @NonNull Account.AccountId ownerAccountId,
            @NonNull Account.AccountId sourceAccountId,
            @NonNull Account.AccountId targetAccountId,
            @NonNull LocalDateTime timestamp,
            @NonNull Money money
            ) {
        this.ownerAccountId = ownerAccountId;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.timestamp = timestamp;
        this.money = money;
    }

    public record ActivityId(Long value) {}
}
