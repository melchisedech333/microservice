
package system.microservice.application.command;

public class DebitCommand implements Command {
    private String operation = "debit";
    private String document = "";
    private int amount = 0;

    public DebitCommand(String document, int amount) {
        this.document = document;
        this.amount = amount;
    }

    @Override
    public String getOperation() {
        return this.operation;
    }

    @Override    
    public String getDocument() {
        return this.document;
    }

    @Override    
    public int getAmount() {
        return this.amount;
    }
}