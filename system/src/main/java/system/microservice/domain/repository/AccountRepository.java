
package system.microservice.domain.repository;

import java.util.ArrayList;

import system.microservice.domain.entity.Account;
import system.microservice.domain.entity.Transaction;

public interface AccountRepository {
    public void save(Account account);
    public Account get(String document);
    public void saveTransaction(Transaction transaction);
    public ArrayList<Transaction> getTransactions(String document);
}

