
package system.microservice.application.command;

public interface Command {
    public String getOperation();
    public String getDocument();
    public int getAmount();
}
