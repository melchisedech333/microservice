
package system.microservice.infrastructure.configuration;

public class Configuration {
    private String databaseURL = "jdbc:mysql://localhost:3306/microservice?serverTimezone=UTC";
    private String databaseUser = "user"; // Local database testing account.
    private String databasePass = "123";  // Local database testing account.

    public String getDatabaseURL() {
        return this.databaseURL;
    }

    public String getDatabaseUser() {
        return this.databaseUser;
    }

    public String getDatabasePass() {
        return this.databasePass;
    }
}
