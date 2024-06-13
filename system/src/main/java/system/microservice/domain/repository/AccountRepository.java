
package system.microservice.domain.repository;

import system.microservice.domain.entity.Account;

public interface AccountRepository {
    public void save(Account account);
    public Account get(String document);
}

