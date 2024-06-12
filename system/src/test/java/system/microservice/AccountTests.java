
package system.microservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertEquals;

import system.microservice.domain.entity.Account;

class AccountTests {

	private Account account;

	@Test
	@DisplayName("Cria uma nova conta.")
	public void testCreateNewAccount() {
		this.account = new Account("111.222.333-44");
		assertEquals(true, this.account.getAccountStatus());
	}

}
