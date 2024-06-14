
package system.microservice.domain.handler;

import system.microservice.application.command.Command;
import system.microservice.domain.entity.Account;
import system.microservice.domain.repository.AccountRepository;
import system.microservice.domain.service.TransferService;
import system.microservice.infrastructure.queue.Observer;

public class TransferHandler extends Observer {
    private String operation = "transfer";
    AccountRepository accountRepository;

    public TransferHandler(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public String getOperation() {
        return this.operation;
    }

    @Override
    public void notify(Command command) {
        Account accountFrom = this.accountRepository.get(command.getDocumentFrom());
        Account accountTo = this.accountRepository.get(command.getDocumentTo());
        
        if (accountFrom != null && accountTo != null) {
            TransferService transferService = new TransferService();
            transferService.transfer(accountFrom, accountTo, command.getAmount());

            this.accountRepository.save(accountFrom);
            this.accountRepository.save(accountTo);
        }
    }
}
