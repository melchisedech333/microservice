
package system.microservice.domain.handler;

import system.microservice.application.command.Command;
import system.microservice.domain.entity.Account;
import system.microservice.domain.repository.AccountRepository;
import system.microservice.infrastructure.queue.Observer;

public class DebitHandler extends Observer {
    private String operation = "debit";
    AccountRepository accountRepository;

    public DebitHandler(AccountRepository accountRepository) {
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
            account.debit(command.getAmount());
            this.accountRepository.save(account);
        }
    }
}
