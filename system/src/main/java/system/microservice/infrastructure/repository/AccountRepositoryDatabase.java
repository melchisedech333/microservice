
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
import system.microservice.infrastructure.configuration.Configuration;

public class AccountRepositoryDatabase implements AccountRepository {
    private String databaseURL = null;
    private String databaseUser = null;
    private String databasePass = null;
    private Connection connection = null;

    public AccountRepositoryDatabase() {
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
        String sql = 
            "CREATE TABLE IF NOT EXISTS accounts (           "+
            "   id       integer PRIMARY KEY AUTO_INCREMENT, "+
            "   document text    NOT NULL,                   "+
            "   bank     text    NOT NULL,                   "+
            "   branch   text    NOT NULL,                   "+
            "   account  text    NOT NULL,                   "+
            "   status   integer DEFAULT 0,                  "+
            "   balance  integer DEFAULT 0                   "+
            ");";
        
        try {
            Statement stmt = this.connection.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            System.out.println("prepareTables(): "+ e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void save(Account account) {
        if (this.checkAccountExists(account.getDocument())) {
            this.updateAccount(account);
        } else {
            this.createNewAccount(account);
        }
    }

    private void createNewAccount(Account account) {
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
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("createNewAccount(): "+ e.getMessage());
        }
    }
    
    public boolean checkAccountExists(String document) {
        String sql = "SELECT * FROM accounts WHERE document = '"+ document +"'";
        
        try {
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                if (rs.getString("document").equals(document)) {
                    rs.close();
                    stmt.close();
                    return true;
                }
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("checkAccountExists(): "+ e.getMessage());
        }

        return false;
    }

    public void updateAccount(Account account) {
        String sql = 
            "UPDATE accounts SET bank = ? , "+
            "branch = ? , account = ? , status = ? , balance = ? "+
            "WHERE document = ? ;";

        int accountStatus = 0;
        if (account.getAccountStatus()) {
            accountStatus = 1;
        }

        try {
            PreparedStatement pstmt = this.connection.prepareStatement(sql);

            pstmt.setString(1, account.getBank());
            pstmt.setString(2, account.getBranch());
            pstmt.setString(3, account.getAccount());
            pstmt.setInt(4, accountStatus);
            pstmt.setInt(5, account.getBalance());
            pstmt.setString(6, account.getDocument());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("updateAccount(): "+ e.getMessage());
        }
    }

    @Override
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

                    rs.close();
                    stmt.close();

                    return account;
                }
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("get(): "+ e.getMessage());
        }

        return null;
    }
}
