
package system.microservice.infrastructure.queue;

import system.microservice.application.command.Command;

public abstract class Observer {
    public abstract String getOperation();
    public abstract void notify(Command command);
}
