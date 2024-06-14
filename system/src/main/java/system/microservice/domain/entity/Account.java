
package system.microservice.domain.entity;

import system.microservice.domain.builder.AccountBuilder;

public class Account {
    private boolean accountStatus = false;
    private int accountBalance = 0;

    private String document;
    private String bank;
    private String branch;
    private String account;

    public Account(AccountBuilder accountBuilder) {
        this.document = accountBuilder.getDocument();
        this.bank = accountBuilder.getBank();
        this.branch = accountBuilder.getBranch();
        this.account = accountBuilder.getAccount();
        this.accountStatus = true;
    }

    public boolean getAccountStatus() {
        return this.accountStatus;
    }

    public int getBalance() {
        return this.accountBalance;
    }

    public String getDocument() {
        return this.document;
    }

    public String getBank() {
        return this.bank;
    }

    public String getBranch() {
        return this.branch;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccountStatus(boolean accountStatus) {
        this.accountStatus = accountStatus;
    }

    public void setBalance(int accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getAccountInformations() {
        return 
            "Document: "+ this.document +"\n"+
            "Bank....: "+ this.bank +"\n"+
            "Branch..: "+ this.branch +"\n"+
            "Account.: "+ this.account +"\n"+
            "Cash....: R$ "+ this.accountBalance;
    }

    public void credit(int amount) {
        this.accountBalance += amount;
    }

    public void debit(int amount) {
        this.accountBalance -= amount;
    }
}
