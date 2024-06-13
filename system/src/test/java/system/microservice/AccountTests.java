
package system.microservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import system.microservice.application.service.AccountApplicationService;
import system.microservice.domain.entity.Account;
import system.microservice.infrastructure.queue.Publisher;
import system.microservice.infrastructure.repository.AccountRepositoryMemory;

class AccountTests {

	private AccountApplicationService service;

	public AccountTests() {
		Publisher publisher = new Publisher();
		AccountRepositoryMemory accountRepositoryMemory = new AccountRepositoryMemory();
		this.service = new AccountApplicationService(publisher, accountRepositoryMemory);
	}

	@Test
	@DisplayName("Cria uma nova conta.")
	public void createAccount() {
		this.service.create(
			"111.111.111-11", 
			"123", 
			"1234", 
			"12345-0");

		Account account = this.service.get("111.111.111-11");
		assertEquals(0, account.getBalance());
	}
}
