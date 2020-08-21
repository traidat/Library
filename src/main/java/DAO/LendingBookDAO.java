package DAO;

import Entity.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LendingBookDAO {

    public boolean addLendingBook(int bookID, Account account) {
        Connect connect = new Connect();
        LocalDate lendingDate = LocalDate.now();
        try {
            try (Connection con = connect.connectDB();
                 PreparedStatement stmt = con.prepareStatement("Insert into `LendingBook` (bookID, username, lendingDate" +
                         ", dueDate, bookStatus) " +
                         "values (?, ?, ?, ?, ?)");) {
                stmt.setInt(1, bookID);
                stmt.setString(2, account.getUsername());
                stmt.setDate(3, Date.valueOf(lendingDate));
                stmt.setDate(4, Date.valueOf(lendingDate.plusDays(10)));
                stmt.setString(5, "Lended");
                int isAdd = stmt.executeUpdate();
                return isAdd > 0;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public Optional<LendingBook> getLendingBook(int bookID)  {
        Connect connect = new Connect();
        LocalDate lendingDate = LocalDate.now();
        try {
            try (Connection con = connect.connectDB();
                 PreparedStatement stmt = con.prepareStatement("Select * from `LendingBook` where bookID = ?")){
                stmt.setInt(1, bookID);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    LendingBook lendingBook = lendingRowMapper(rs);
                    return Optional.ofNullable(lendingBook);
                }
            }
        } catch (SQLException throwables) {
            System.out.println("Error when connect database");
        }
        return Optional.empty();
    }

    public boolean updateStatus(LendingBook lendingBook) {
        Connect connect = new Connect();
        try {
            try (Connection con = connect.connectDB();
                 PreparedStatement stmt = con.prepareStatement("Update `Book` set bookStatus = ? where bookID = ?")) {
                stmt.setInt(2, lendingBook.getBookID());
                stmt.setString(1, "Not available");
                int isAdd = stmt.executeUpdate();
                return isAdd > 0;
            }
        } catch (SQLException throwables) {
            System.out.println("Error when connect database");
        }
        return false;
    }

    public Optional<List<LendingBook>> getListLendingBook(String username) {
        Connect connect = new Connect();
        try {
            try (Connection con = connect.connectDB();
                 PreparedStatement stmt = con.prepareStatement("Select * from `LendingBook` where username = ? " +
                         "and bookStatus = ?")) {
                stmt.setString(1, username);
                stmt.setString(2, "Lended");
                ResultSet rs = stmt.executeQuery();
                List<LendingBook> lendingBooks = new ArrayList<>();
                while (rs.next()) {
                    lendingBooks.add(lendingRowMapper(rs));
                }
                return Optional.ofNullable(lendingBooks);
            }
        } catch (SQLException throwables) {
            System.out.println("Error when connect database");
        }
        return Optional.empty();
    }

    public boolean updateAvailable(LendingBook lendingBook) {
        Connect connect = new Connect();
        try {
            try (Connection con = connect.connectDB();
                 PreparedStatement stmt = con.prepareStatement("Update `Book` set bookStatus = ? where bookID = ?");
                 PreparedStatement stmt1 = con.prepareStatement("Update `LendingBook` set bookStatus = ? " +
                         "where bookId = ?")) {
                stmt.setString(1, "Available");
                stmt.setInt(2, lendingBook.getBookID());
                stmt1.setString(1, "Returned");
                stmt1.setInt(2, lendingBook.getBookID());
                int isAdd = stmt.executeUpdate();
                int isAdd1 = stmt1.executeUpdate();
                return isAdd > 0 && isAdd1 > 0;
            }
        } catch (SQLException throwables) {
            System.out.println("Error when connect database");
        }
        return false;
    }

    public boolean updateReturnDate(LendingBook lendingBook) {
        Connect connect = new Connect();
        LocalDate returnDate = LocalDate.now();
        try {
            try (Connection con = connect.connectDB();
                 PreparedStatement stmt = con.prepareStatement("Update `LendingBook` set returnDate = ? where bookID = ?")) {
                stmt.setInt(2, lendingBook.getBookID());
                stmt.setDate(1, Date.valueOf(returnDate));
                int isUpdate = stmt.executeUpdate();
                return isUpdate > 0;
            }
        } catch (SQLException throwables) {
            System.out.println("Error when connect database");
        }
        return false;
    }

    public LendingBook lendingRowMapper(ResultSet rs) throws SQLException {
        LendingBook lendingBook = new LendingBook(rs.getInt("bookID"), rs.getString("bookStatus"),
                rs.getString("username"), rs.getDate("lendingDate").toLocalDate(),
                rs.getDate("dueDate").toLocalDate());
        return lendingBook;
    }


}
