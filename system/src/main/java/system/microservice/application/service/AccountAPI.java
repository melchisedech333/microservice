
package system.microservice.application.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import system.microservice.domain.entity.Account;
import system.microservice.infrastructure.queue.Publisher;
import system.microservice.infrastructure.repository.AccountRepositoryDatabase;

@RestController
public class AccountAPI {
    private AccountApplicationService service;

    public AccountAPI() {
        Publisher publisher = new Publisher();
        AccountRepositoryDatabase accountRepository = new AccountRepositoryDatabase();
        this.service = new AccountApplicationService(publisher, accountRepository);
    }

    @GetMapping("/create/{document}/{bank}/{branch}/{accountNumber}")
    public String create(
        @PathVariable String document,
        @PathVariable String bank,
        @PathVariable String branch,
        @PathVariable String accountNumber)
    {
        this.service.create(document, bank, branch, accountNumber);
        Account account = this.service.get(document);

        if (account != null) {
            if (account.getAccountStatus()) {
                return "Account created.";
            }
        }

        return "Error creating account.";
    }

    @GetMapping("/informations/{document}")
    public String informations(@PathVariable String document) {
        Account account = this.service.get(document);
		
        if (account != null) {
            return "<pre>"+ account.getAccountInformations() +"</pre>";
        }

        return "Account not found.";
    }
}
