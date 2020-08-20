package DAO;

import Entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class PersonDAO {

    public boolean addPersonToDB(String name, String phoneNumber, String email, String address) {
        Connect connect = new Connect();
        try {
            try (Connection con = connect.connectDB();
                 PreparedStatement stmt = con.prepareStatement("Insert into `Person` (name, phoneNumber, email, address) " +
                         "values (?, ?, ?, ?)")) {
                stmt.setString(1, name);
                stmt.setString(2, phoneNumber);
                stmt.setString(3, email);
                stmt.setString(4, address);
                int rowAdd = stmt.executeUpdate();
                return rowAdd > 0;
            }
        } catch (SQLException throwables) {
            System.out.println("Error when connect database");
        }
        return false;
    }

    public Optional<Person> getPerson(String email)  {
        Connect connect = new Connect();
        try {
            try (Connection con = connect.connectDB();
                 PreparedStatement stmt = con.prepareStatement("Select * from `Person` where email = ?")) {
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Person person = personRowMapper(rs);
                    return Optional.of(person);
                }
            }
        } catch (SQLException throwables) {
            System.out.println("Error when connect database");
        }
        return Optional.empty();
    }

    public Optional<Person> getPerson(int id) {
        Connect connect = new Connect();
        try {
            try (Connection con = connect.connectDB();
                 PreparedStatement stmt = con.prepareStatement("Select * from `Person` where personID = ?")) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Person person = personRowMapper(rs);
                    return Optional.of(person);
                }
            }
        } catch (SQLException throwables) {
            System.out.println("Error when connect database");
        }
        return Optional.empty();
    }

    public Person personRowMapper(ResultSet rs) throws SQLException {
        Person person = new Person(rs.getInt("personID"), rs.getString("name"), rs.getString("phoneNumber"),
                rs.getString("email"), rs.getString("address"));
        return person;
    }

}
