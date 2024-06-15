
package system.microservice.domain.entity;

import java.util.ArrayList;

import system.microservice.domain.builder.AccountBuilder;
import system.microservice.infrastructure.repository.AccountRepositoryDatabase;

public class Account {
    
    private boolean accountStatus = false;
    private String document;
    private String bank;
    private String branch;
    private String account;

    private AccountRepositoryDatabase accountRepository = new AccountRepositoryDatabase();
    
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

    public String getAccountInformations() {
        return 
            "Document: "+ this.document +"\n"+
            "Bank....: "+ this.bank +"\n"+
            "Branch..: "+ this.branch +"\n"+
            "Account.: "+ this.account +"\n"+
            "Cash....: R$ "+ this.getBalance();
    }

    public int getBalance() {
        ArrayList<Transaction> transactions = 
            this.accountRepository.getTransactions(this.document);
        int balance = 0;
        int amount = 0;

        for (var a=0; a<transactions.size(); a++) {
            amount = transactions.get(a).getAmount();

            if (transactions.get(a).getOperation().equals("credit")) {
                balance += amount;
            } else if (transactions.get(a).getOperation().equals("debit")) {
                balance -= amount;
            }
        }

        return balance;
    }

    public void credit(int amount) {
        Transaction transaction = new Transaction(this.document, "credit", amount);
        this.accountRepository.saveTransaction(transaction);
    }

    public void debit(int amount) {
        Transaction transaction = new Transaction(this.document, "debit", amount);
        this.accountRepository.saveTransaction(transaction);
    }
}
