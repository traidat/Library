package DAO;

import Entity.*;
import Service.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class AccountDAO {
    public Optional<Account> getAccount(String identity, String pwd) {
        Connect connect = new Connect();
        try {
            try (Connection con = connect.connectDB();
                 PreparedStatement stmt = con.prepareStatement("Select * from `Account` where username = ? and password = ?")) {
                stmt.setString(1, identity);
                stmt.setString(2, pwd);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    PersonService personService = new PersonService();
                    if (personService.getPerson(rs.getInt("personID")).isPresent()) {
                        Person person = personService.getPerson(rs.getInt("personID")).get();
                        Account account = accountRowMapper(rs, person);
                        return Optional.ofNullable(account);
                    }
                }
            }
        } catch (SQLException throwables) {
            System.out.println("Error when connect database");
        }
        return Optional.empty();
    }

    public Optional<Account> getAccount(String username)  {
        Connect connect = new Connect();
        try {
            try (Connection con = connect.connectDB();
                 PreparedStatement stmt = con.prepareStatement("Select * from `Account` where username = ?")) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    PersonService personService = new PersonService();
                    if (personService.getPerson(rs.getInt("personID")).isPresent()) {
                        Person person = personService.getPerson(rs.getInt("personID")).get();
                        LendingBookService lendingBookService = new LendingBookService();
                        person.setBookLending(lendingBookService.getLendedBook(username));
                        Account account = accountRowMapper(rs, person);
                        return Optional.ofNullable(account);
                    }
                }
            }
        } catch (SQLException throwables) {
            System.out.println("Error when connect database");
        }
        return Optional.empty();
    }

    public boolean addAccount(Person person, String username, String password, String accountStatus, String accountType) {
        LocalDate createdOn = LocalDate.now();

        Connect connect = new Connect();
        try {
            try (Connection con = connect.connectDB()) {
                PreparedStatement stmt = con.prepareStatement("Insert into `Account` (personID, username, password, " +
                        "accountStatus, createdOn, accountType) values (?, ?, ?, ?, ?, ?)");
                stmt.setInt(1, person.getPersonID());
                stmt.setString(2, username);
                stmt.setString(3, password);
                stmt.setString(4, accountStatus);
                stmt.setDate(5, Date.valueOf(createdOn));
                stmt.setString(6, accountType);
                int isAdd = stmt.executeUpdate();
                return isAdd > 0;
            }
        } catch (SQLException throwables) {
            System.out.println("Error when connect database");
        }
        return false;
    }

    public Account accountRowMapper(ResultSet rs, Person person) throws SQLException {
        Account account = new Account(person, rs.getString("username"), rs.getString("password"),
                rs.getString("accountStatus"), rs.getString("accountType"));
        return account;
    }

}
