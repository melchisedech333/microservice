
package system.microservice.domain.entity;

public class Transaction {
    private String document;
    private String operation;
    private int amount;

    public Transaction(String document, String operation, int amount) {
        this.document = document;
        this.operation = operation;
        this.amount = amount;
    }

    public String getDocument() {
        return this.document;
    }

    public String getOperation() {
        return this.operation;
    }

    public int getAmount() {
        return this.amount;
    }
}
