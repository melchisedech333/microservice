
package system.microservice.application.service;

import system.microservice.infrastructure.queue.Publisher;
import system.microservice.domain.repository.AccountRepository;
import system.microservice.domain.builder.AccountBuilder;
import system.microservice.domain.entity.Account;

public class AccountApplicationService {
    private Publisher publisher;
    private AccountRepository accountRepository;

    public AccountApplicationService(Publisher publisher, AccountRepository accountRepository) {
        this.publisher = publisher;
        this.accountRepository = accountRepository;
    }

    public void create(String document, String bank, String branch, String accountNumber) {
        AccountBuilder accountBuilder = new AccountBuilder(document);
        Account account = accountBuilder
            .setBank(bank)
            .setBranch(branch)
            .setAccount(accountNumber)
            .build();

        this.accountRepository.save(account);
    }

    public Account get(String document) {
        return this.accountRepository.get(document);
    }
}