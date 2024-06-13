
package system.microservice.domain.builder;

import system.microservice.domain.entity.Account;

public class AccountBuilder {
    private String document = "";
    private String bank = "";
    private String branch = "";
    private String account = "";

    public AccountBuilder(String document) {
        this.document = document;
    }

    public AccountBuilder setBank(String bank) {
        this.bank = bank;
        return this;
    }

    public AccountBuilder setBranch(String branch) {
        this.branch = branch;
        return this;
    }

    public AccountBuilder setAccount(String account) {
        this.account = account;
        return this;
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

    public Account build() {
        var account = new Account(this);
        return account;
    }
}
