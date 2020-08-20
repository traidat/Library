package DAO;

import Entity.*;

import java.sql.*;
import java.util.Optional;

public class AuthorDAO {

    public boolean addAuthor(String authorName, String authorDetail)  {
        Connect connect = new Connect();
        try {
            try (Connection con = connect.connectDB()) {
                PreparedStatement stmt = con.prepareStatement("Insert into `Author` (authorName, authorDetail) values (?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, authorName);
                stmt.setString(2, authorDetail);
                int id = 0;
                int isAdd = stmt.executeUpdate();
                return isAdd > 0;
            }
        } catch (SQLException throwables) {
            System.out.println("Error when connect database");
        }
        return false;
    }

    public Optional<Author> getAuthor(String authorName, String authorDetail)  {
        Connect connect = new Connect();
        try {
            try (Connection con = connect.connectDB()) {
                PreparedStatement stmt = con.prepareStatement("Select * from `Author` where authorName = ? " +
                        "and authorDetail = ?");
                stmt.setString(1, authorName);
                stmt.setString(2, authorDetail);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    Author author = new Author(rs.getInt("authorID"), authorName, authorDetail);
                    return Optional.ofNullable(author);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException throwables) {
            System.out.println("Error when connect database");
        }
        return Optional.empty();
    }

}
