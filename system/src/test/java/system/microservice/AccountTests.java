
package system.microservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import system.microservice.domain.entity.Account;

class AccountTests {

	@Test
	@DisplayName("Cria uma nova conta.")
	public void testCreateNewAccount() {
		Account account = new Account("111.222.333-44");
		assertTrue(account.getAccountStatus());
	}

	@Test
	@DisplayName("Verifica balance da conta recém criada.")
	public void testCheckBalanceNewAccount() {
		Account account = new Account("111.222.333-44");
		assertEquals(0, account.getBalance());
	}

}
