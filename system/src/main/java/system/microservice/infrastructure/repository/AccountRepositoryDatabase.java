
package system.microservice.infrastructure.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import system.microservice.domain.builder.AccountBuilder;
import system.microservice.domain.entity.Account;
import system.microservice.domain.repository.AccountRepository;

public class AccountRepositoryDatabase implements AccountRepository {

    private String databaseURL = "jdbc:sqlite:database.db";
    private Connection connection = null;

    public AccountRepositoryDatabase() {
        if (!this.connectDatabase()) {
            System.out.println("Error connect database.");
            System.exit(1);
        }

        this.prepareTables();
    }

    private boolean connectDatabase() {
        try {
            this.connection = DriverManager.getConnection(this.databaseURL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            
            try {
                if (this.connection != null) {
                    this.connection.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

            return false;
        }

        return true;
    }

    private void prepareTables() {
        String sql = 
            "CREATE TABLE IF NOT EXISTS accounts ( "+
            "   id       integer PRIMARY KEY,      "+
            "   document text    NOT NULL,         "+
            "   bank     text    NOT NULL,         "+
            "   branch   text    NOT NULL,         "+
            "   account  text    NOT NULL,         "+
            "   status   integer DEFAULT 0,        "+
            "   balance  integer DEFAULT 0         "+
            ");";
        
        try {
            this.connection = DriverManager.getConnection(this.databaseURL);
            Statement stmt = this.connection.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public void save(Account account) {
        if (this.checkAccountExists(account.getDocument())) {
            return;
        }

        String sql = 
            "INSERT INTO accounts (document, bank, branch, account, status, balance) "+
            "VALUES (?,?,?,?,?,?);";

        int accountStatus = 0;
        if (account.getAccountStatus()) {
            accountStatus = 1;
        }

        try {
            PreparedStatement pstmt = this.connection.prepareStatement(sql);

            pstmt.setString(1, account.getDocument());
            pstmt.setString(2, account.getBank());
            pstmt.setString(3, account.getBranch());
            pstmt.setString(4, account.getAccount());
            pstmt.setInt(5, accountStatus);
            pstmt.setInt(6, account.getBalance());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public boolean checkAccountExists(String document) {
        String sql = "SELECT * FROM accounts WHERE document = '"+ document +"'";

        try {
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                if (rs.getString("document").equals(document)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public Account get(String document) {
        String sql = "SELECT * FROM accounts WHERE document = '"+ document +"'";
        AccountBuilder accountBuilder = new AccountBuilder(document);
        
        try {
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                if (rs.getString("document").equals(document)) {
                    
                    Account account = accountBuilder
                        .setBank(rs.getString("bank"))
                        .setBranch(rs.getString("branch"))
                        .setAccount(rs.getString("account"))
                        .build();

                    boolean accountStatus = false;
                    if (rs.getInt("status") == 1) {
                        accountStatus = true;
                    }
                    
                    account.setAccountStatus(accountStatus);
                    account.setBalance(rs.getInt("balance"));

                    return account;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
