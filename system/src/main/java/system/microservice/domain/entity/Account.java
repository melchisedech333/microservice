
package system.microservice.domain.entity;

public class Account {
    private boolean accountStatus = false;

    public Account(String document) {
        this.accountStatus = true;
    }

    public boolean getAccountStatus() {
        return this.accountStatus;
    }
}
