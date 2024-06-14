
package system.microservice.integration;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

import system.microservice.application.service.AccountApplicationService;
import system.microservice.domain.entity.Account;
import system.microservice.domain.handler.CreditHandler;
import system.microservice.domain.handler.DebitHandler;
import system.microservice.domain.handler.TransferHandler;
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

		publisher.register(new CreditHandler(accountRepository));
		publisher.register(new DebitHandler(accountRepository));
		publisher.register(new TransferHandler(accountRepository));

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
		assertTrue(informations.contains("111.111.111-11"));
		this.log.save(informations);
	}

	@Test
	@Order(3)
	@DisplayName("Integration: crédito em conta (mais 1000).")
	public void creditCash() {
		this.service.credit("111.111.111-11", 1000);
		Account account = this.service.get("111.111.111-11");
		assertEquals(1000, account.getBalance());
		
		if (account != null) {
			String informations = account.getAccountInformations();
			this.log.save(informations);
		}
	}

	@Test
	@Order(4)
	@DisplayName("Integration: crédito em conta (mais 700).")
	public void creditCashMore() {
		this.service.credit("111.111.111-11", 500);
		this.service.credit("111.111.111-11", 200);
		Account account = this.service.get("111.111.111-11");
		assertEquals(1700, account.getBalance());
		
		if (account != null) {
			String informations = account.getAccountInformations();
			this.log.save(informations);
		}
	}

	@Test
	@Order(5)
	@DisplayName("Integration: dédito em conta (menos 300).")
	public void deditCash() {
		this.service.debit("111.111.111-11", 300);
		Account account = this.service.get("111.111.111-11");
		assertEquals(1400, account.getBalance());
		
		if (account != null) {
			String informations = account.getAccountInformations();
			this.log.save(informations);
		}
	}

	@Test
	@Order(6)
	@DisplayName("Integration: transferência entre contas.")
	public void transferCash() {

		// Create accounts.
		this.service.create(
			"333.333.333-33", 
			"123", 
			"1234", 
			"12345-0");

		this.service.create(
			"444.444.444-44", 
			"123", 
			"1234", 
			"12345-0");

		// Credit cash.
		this.service.credit("333.333.333-33", 1000);
		this.service.credit("444.444.444-44", 1000);

		// Transfer cash.
		this.service.transfer(
			"333.333.333-33", 
			"444.444.444-44", 
			300
		);

		// Get accounts.
		Account accountFrom = this.service.get("333.333.333-33");
		Account accountTo = this.service.get("444.444.444-44");

		if (accountFrom != null && accountTo != null) {

			// Check balance.
			assertEquals(700, accountFrom.getBalance());
			assertEquals(1300, accountTo.getBalance());

			// Get informations.
			String informationsFrom = accountFrom.getAccountInformations();
			String informationsTo = accountTo.getAccountInformations();
			
			this.log.save(
				"-> Account From: \n"+ informationsFrom +"\n\n"+
				"-> Account To: \n"+ informationsTo
			);
		}
	}
}
