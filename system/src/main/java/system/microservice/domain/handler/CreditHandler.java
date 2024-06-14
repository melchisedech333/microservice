
package system.microservice.domain.handler;

import system.microservice.application.command.Command;
import system.microservice.domain.entity.Account;
import system.microservice.domain.repository.AccountRepository;
import system.microservice.infrastructure.queue.Observer;

public class CreditHandler extends Observer {
    private String operation = "credit";
    AccountRepository accountRepository;

    public CreditHandler(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public String getOperation() {
        return this.operation;
    }

    @Override
    public void notify(Command command) {
        Account account = this.accountRepository.get(command.getDocument());
        if (account != null) {
            account.credit(command.getAmount());
            this.accountRepository.save(account);
        }
    }
}
