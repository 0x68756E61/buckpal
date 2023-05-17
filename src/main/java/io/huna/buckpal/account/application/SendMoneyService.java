package io.huna.buckpal.account.application;

import io.huna.buckpal.account.application.port.in.SendMoneyCommand;
import io.huna.buckpal.account.application.port.in.SendMoneyUseCase;
import io.huna.buckpal.account.application.port.out.AccountLock;
import io.huna.buckpal.account.application.port.out.LoadAccountPort;
import io.huna.buckpal.account.application.port.out.UpdateAccountStatePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Transactional
@RequiredArgsConstructor
public class SendMoneyService implements SendMoneyUseCase {

    private final LoadAccountPort loadAccountPort;

    private final AccountLock accountLock;

    private final UpdateAccountStatePort updateAccountStatePort;

    private final MoneyTransferProperties moneyTransferProperties;

    @Override
    public boolean sendMoney(SendMoneyCommand command) {
        this.checkThreshold(command);

        var baselineDate = LocalDateTime.now().minusDays(10);
        var sourceAccount = loadAccountPort.loadAccount(command.getSourceAccountId(), baselineDate);
        var targetAccount = loadAccountPort.loadAccount(command.getTargetAccountId(), baselineDate);
        var sourceAccountId = sourceAccount.getId()
                .orElseThrow(() -> new IllegalStateException("expected source account ID not to be empty"));
        var targetAccountId = targetAccount.getId()
                .orElseThrow(() -> new IllegalStateException("expected target account ID not to be empty"));

        accountLock.lockAccount(sourceAccountId);
        if (!sourceAccount.withdraw(command.getMoney(), targetAccountId)) {
            accountLock.releaseAccount(sourceAccountId);
            return false;
        }

        accountLock.lockAccount(targetAccountId);
        if (!targetAccount.deposit(command.getMoney(), sourceAccountId)) {
            accountLock.releaseAccount((sourceAccountId));
            accountLock.releaseAccount((targetAccountId));
            return false;
        }

        updateAccountStatePort.updateActivities(sourceAccount);
        updateAccountStatePort.updateActivities(targetAccount);

        accountLock.releaseAccount(sourceAccountId);
        accountLock.releaseAccount(targetAccountId);
        return true;
    }

    private void checkThreshold(SendMoneyCommand command) {
        if (command.getMoney().isGreaterThan(moneyTransferProperties.getMaximumTransferThreshold())) {
            throw new ThresholdExceededException(moneyTransferProperties.getMaximumTransferThreshold(), command.getMoney());
        }
    }
}
