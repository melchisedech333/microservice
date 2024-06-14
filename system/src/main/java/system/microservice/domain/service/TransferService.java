
package system.microservice.domain.service;

import system.microservice.domain.entity.Account;

public class TransferService {
    public void transfer(Account accountFrom, Account accountTo, int amount) {
        accountFrom.debit(amount);
        accountTo.credit(amount);
    }
}
