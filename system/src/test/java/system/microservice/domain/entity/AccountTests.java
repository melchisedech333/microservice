
package system.microservice.domain.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertEquals;

import system.microservice.domain.entity.Account;

class AccountTests {

	@Test
	@DisplayName("Cria uma nova conta.")
	public void testCreateNewAccount() {
		Account account = new Account("111.222.333-44");
		assertEquals(true, account.getAccountStatus());
	}

}
