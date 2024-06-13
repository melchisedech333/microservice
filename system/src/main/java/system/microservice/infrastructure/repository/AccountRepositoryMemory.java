
package system.microservice.infrastructure.repository;

import java.util.ArrayList;

import system.microservice.domain.entity.Account;
import system.microservice.domain.repository.AccountRepository;

public class AccountRepositoryMemory implements AccountRepository {
    private ArrayList<Account> accounts = new ArrayList<Account>();

    public void save(Account account) {
        this.accounts.add(account);
    }
    
    public Account get(String document) {
        for (var a=0; a<accounts.size(); a++) {
            if (accounts.get(a).getDocument() == document) {
                return accounts.get(a);
            }
        }

        return null;
    }
}
