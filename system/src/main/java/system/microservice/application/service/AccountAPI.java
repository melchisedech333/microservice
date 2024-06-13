
package system.microservice.application.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import system.microservice.domain.entity.Account;
import system.microservice.infrastructure.queue.Publisher;
import system.microservice.infrastructure.repository.AccountRepositoryMemory;

@RestController
public class AccountAPI {
    
	private AccountApplicationService service;

	public AccountAPI() {
		Publisher publisher = new Publisher();
		AccountRepositoryMemory accountRepositoryMemory = new AccountRepositoryMemory();
		this.service = new AccountApplicationService(publisher, accountRepositoryMemory);
	}

    @RequestMapping("/create")
    public String create() {
		this.service.create(
			"111.111.111-11", 
			"123", 
			"1234", 
			"12345-0");

		Account account = this.service.get("111.111.111-11");
		
        if (account != null) {
            if (account.getAccountStatus()) {
                return "Account created.";
            }
        }

        return "Error creating account.";
    }

    @RequestMapping("/informations")
    public String informations() {
		Account account = this.service.get("111.111.111-11");
		
        if (account != null) {
            return "<pre>"+ account.getAccountInformations() +"</pre>";
        }

        return "Account not found.";
    }
}
