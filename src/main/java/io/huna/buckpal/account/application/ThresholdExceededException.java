package io.huna.buckpal.account.application;

import io.huna.buckpal.account.domain.Money;
import lombok.NonNull;

public class ThresholdExceededException extends RuntimeException {

    public ThresholdExceededException(Money threshold, @NonNull Money actual) {
        super(String.format("Maximum threshold for transferring money exceeded: tried to transfer %s but threshold is %s!", actual, threshold));
    }
}