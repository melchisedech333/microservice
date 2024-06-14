
package system.microservice.application.command;

public class TransferCommand implements Command {
    private String operation = "transfer";
    private String documentFrom = "";
    private String documentTo = "";
    private int amount = 0;

    public TransferCommand(String documentFrom, String documentTo, int amount) {
        this.documentFrom = documentFrom;
        this.documentTo = documentTo;
        this.amount = amount;
    }

    @Override
    public String getOperation() {
        return this.operation;
    }

    @Override    
    public String getDocument() {
        return null;
    }

    @Override    
    public String getDocumentFrom() {
        return this.documentFrom;
    }

    @Override    
    public String getDocumentTo() {
        return this.documentTo;
    }

    @Override    
    public int getAmount() {
        return this.amount;
    }
}
