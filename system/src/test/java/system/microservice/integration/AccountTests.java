
package system.microservice.integration;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

import system.microservice.application.service.AccountApplicationService;
import system.microservice.domain.entity.Account;
import system.microservice.infrastructure.queue.Publisher;
import system.microservice.infrastructure.repository.AccountRepositoryDatabase;
import system.microservice.library.*;

@TestMethodOrder(OrderAnnotation.class)
class AccountTests {
	
	private Log log = new Log(AccountTests.class.getName(), "Integration-Account");
	private AccountApplicationService service;

	public AccountTests() {
		Publisher publisher = new Publisher();
		AccountRepositoryDatabase accountRepository = new AccountRepositoryDatabase();
		this.service = new AccountApplicationService(publisher, accountRepository);
	}

	@Test
	@Order(1)
	@DisplayName("Integration: cria uma nova conta.")
	public void createAccount() {
		this.service.create(
			"111.111.111-11", 
			"123", 
			"1234", 
			"12345-0");

		Account account = this.service.get("111.111.111-11");
		assertTrue(account.getAccountStatus());
		
		if (account != null) {
			this.log.save(
				"Account created!\n"+
				"Account status: "+ account.getAccountStatus());
		}
	}

	@Test
	@Order(2)
	@DisplayName("Integration: retorna informações da conta.")
	public void getAccountInformations() {
		Account account = this.service.get("111.111.111-11");
		String informations = account.getAccountInformations();
		assertEquals(true, informations.contains("111.111.111-11"));
		this.log.save(informations);
	}
}
