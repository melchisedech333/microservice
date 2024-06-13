
package system.microservice.domain.entity;

public class Account {
    private boolean accountStatus = false;
    private int accountBalance = 0;

    public Account(String document) {
        this.accountStatus = true;
    }

    public boolean getAccountStatus() {
        return this.accountStatus;
    }

    public int getBalance() {
        return this.accountBalance;
    }
}
