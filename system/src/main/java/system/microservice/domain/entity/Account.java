
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

    public String getAccountInformations() {
        return 
            "Document: "+ this.document +"\n"+
            "Bank....: "+ this.bank +"\n"+
            "Branch..: "+ this.branch +"\n"+
            "Account.: "+ this.account;
    }

    public void credit(int amount) {

    }

    public void dedit(int amount) {
        
    }
}
