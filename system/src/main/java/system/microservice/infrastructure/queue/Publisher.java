
package system.microservice.infrastructure.queue;

import java.util.ArrayList;

import system.microservice.application.command.Command;

public class Publisher {
    ArrayList<Observer> observers = new ArrayList<Observer>();

    public void register(Observer observer) {
        this.observers.add(observer);
    }

    public void publish(Command command) {
        for (var a=0; a<this.observers.size(); a++) {
            if (this.observers.get(a).getOperation() == command.getOperation()) {
                this.observers.get(a).notify(command);
            }
        }
    }
}
