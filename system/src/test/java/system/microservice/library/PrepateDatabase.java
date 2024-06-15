
package system.microservice.library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import system.microservice.infrastructure.configuration.Configuration;

public class PrepateDatabase {
    private String databaseURL = null;
    private String databaseUser = null;
    private String databasePass = null;
    private Connection connection = null;

    public void prepare() {
        this.prepareConfigurations();

        if (!this.connectDatabase()) {
            System.out.println("Error connect database.");
            System.exit(1);
        }

        this.prepareTables();
    }

    private void prepareConfigurations() {
        Configuration configuration = new Configuration();
        this.databaseURL = configuration.getDatabaseURL();
        this.databaseUser = configuration.getDatabaseUser();
        this.databasePass = configuration.getDatabasePass();
    }

    private boolean connectDatabase() {
        try {
            this.connection = DriverManager.getConnection(
                this.databaseURL, this.databaseUser, this.databasePass);
        } catch (SQLException e) {
            System.err.println("connectDatabase(): "+ e.getMessage());
            
            try {
                if (this.connection != null) {
                    this.connection.close();
                }
            } catch (SQLException ex) {
                System.err.println("connectDatabase() exception: "+ ex.getMessage());
            }

            return false;
        }

        return true;
    }

    private void prepareTables() {
        String sqlAccounts = "DROP TABLE IF EXISTS accounts;";
        String sqlTransactions = "DROP TABLE IF EXISTS transactions;";

        try {
            Statement stmt = this.connection.createStatement();
            stmt.execute(sqlAccounts);
            stmt.close();
        } catch (SQLException e) {
            System.out.println("prepareTables(): Accounts - "+ e.getMessage());
            System.exit(1);
        }

        try {
            Statement stmt = this.connection.createStatement();
            stmt.execute(sqlTransactions);
            stmt.close();
        } catch (SQLException e) {
            System.out.println("prepareTables(): Transactions - "+ e.getMessage());
            System.exit(1);
        }
    }
}
