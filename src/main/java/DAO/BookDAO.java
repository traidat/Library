package DAO;

import Entity.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDAO {

    public boolean addBook(int price, int numberPage, String nameBook, String bookStatus, String language,
                                  Author author, Category category, CodeLocation codeLocation, LocalDate date) {
        Connect connect = new Connect();
        try {
            try (Connection con = connect.connectDB();
                 PreparedStatement stmt = con.prepareStatement("Insert into `Book` (nameBook, bookStatus, codeLocation," +
                         " authorID, categoryID, price, language, numberPage, dateBuy) " +
                         "values (?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS) ){
                stmt.setString(1, nameBook);
                stmt.setString(2, bookStatus);
                stmt.setString(3, codeLocation.getCode());
                stmt.setInt(4, author.getAuthorID());
                stmt.setInt(5, category.getCategoryID());
                stmt.setInt(6, price);
                stmt.setString(7, language);
                stmt.setInt(8, numberPage);
                stmt.setDate(9, Date.valueOf(date));
                int isAdd = stmt.executeUpdate();
                return isAdd > 0;
            }
        } catch (SQLException throwables) {
            System.out.println("Error when connect database");
        }
        return false;
    }

    public boolean modifyBook(int bookID, int price, int numberPage, String nameBook, String bookStatus, String language,
                                  Author author, Category category, LocalDate date)  {
        Connect connect = new Connect();
        try {
            try (Connection con = connect.connectDB();
                 PreparedStatement stmt = con.prepareStatement("Update `Book`  set nameBook = ?, bookStatus = ?," +
                         " authorID = ?, categoryID = ?, price = ?, language = ?, numberPage = ?, dateBuy = ? " +
                         "where bookID = ?") ){
                stmt.setString(1, nameBook);
                stmt.setString(2, bookStatus);
                stmt.setInt(3, author.getAuthorID());
                stmt.setInt(4, category.getCategoryID());
                stmt.setInt(5, price);
                stmt.setString(6, language);
                stmt.setInt(7, numberPage);
                stmt.setDate(8, Date.valueOf(date));
                stmt.setInt(9, bookID);
                int isAdd = stmt.executeUpdate();
                return isAdd > 0;

            }
        } catch (SQLException throwables) {
            System.out.println("Error when connect database");
        }
        return false;
    }

    public Optional<List<Book>> allBook() {
        Connect connect = new Connect();
        try {
            try (Connection con = connect.connectDB();
                 PreparedStatement stmt = con.prepareStatement("select * from Book\n" +
                         "inner join Author on Book.authorID = Author.authorID \n" +
                         "inner join Category on Book.categoryID = Category.categoryID\n" +
                         "inner join CodeLocation on Book.codeLocation = CodeLocation.code order by bookID")) {
                List<Book> allBook = new ArrayList<>();
                ResultSet rs = stmt.executeQuery();
                while ((rs.next())) {
                    Author author = authorRowMapper(rs);
                    Category category = categoryRowMapper(rs);
                    CodeLocation codeLocation = codeRowMapper(rs);
                    Book book = bookRowMapper(rs, author, category, codeLocation);
                    allBook.add(book);
                }
                return Optional.ofNullable(allBook);
            }
        } catch (Exception throwables) {
            System.out.println("Error when connect database");
        }
        return Optional.empty();
    }

    public Optional<Book> getBook(int id) {
        Connect connect = new Connect();
        try {
            try (Connection con = connect.connectDB();
                 PreparedStatement stmt = con.prepareStatement("select * from Book\n" +
                         "inner join Author on Book.authorID = Author.authorID \n" +
                         "inner join Category on Book.categoryID = Category.categoryID\n" +
                         "inner join CodeLocation on Book.codeLocation = CodeLocation.code where Book.bookID = ?")) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                while ((rs.next())) {
                    Author author = authorRowMapper(rs);
                    Category category = categoryRowMapper(rs);
                    CodeLocation codeLocation = codeRowMapper(rs);
                    Book book = bookRowMapper(rs, author, category, codeLocation);
                    return Optional.ofNullable(book);
                }
                return Optional.empty();
            }
        } catch (SQLException throwables) {
            System.out.println("Error when connect database");
        }
        return Optional.empty();
    }

    public Optional<List<Book>> searchByName(String nameBook) {
        Connect connect = new Connect();
        try {
            try (Connection con = connect.connectDB();
                 PreparedStatement stmt = con.prepareStatement("select * from Book\n" +
                         "inner join Author on Book.authorID = Author.authorID \n" +
                         "inner join Category on Book.categoryID = Category.categoryID\n" +
                         "inner join CodeLocation on Book.codeLocation = CodeLocation.code where Book.nameBook like ? ")) {
                List<Book> allBook = new ArrayList<>();
                stmt.setString(1, "%" + nameBook + "%");
                ResultSet rs = stmt.executeQuery();
                while ((rs.next())) {
                    Author author = authorRowMapper(rs);
                    Category category = categoryRowMapper(rs);
                    CodeLocation codeLocation = codeRowMapper(rs);
                    Book book = bookRowMapper(rs, author, category, codeLocation);
                    allBook.add(book);
                }
                return Optional.ofNullable(allBook);
            }
        } catch (SQLException throwables) {
            System.out.println("Error when connect database");
        }
        return Optional.empty();
    }

    public Optional<List<Book>> searchByAuthor(String authorName) {
        Connect connect = new Connect();
        try {
            try (Connection con = connect.connectDB();
                 PreparedStatement stmt = con.prepareStatement("select * from Book\n" +
                         "inner join Author on Book.authorID = Author.authorID \n" +
                         "inner join Category on Book.categoryID = Category.categoryID\n" +
                         "inner join CodeLocation on Book.codeLocation = CodeLocation.code where Author.authorName like ? ")) {
                List<Book> allBook = new ArrayList<>();
                stmt.setString(1, "%" + authorName + "%");
                ResultSet rs = stmt.executeQuery();
                while ((rs.next())) {
                    Author author = authorRowMapper(rs);
                    Category category = categoryRowMapper(rs);
                    CodeLocation codeLocation = codeRowMapper(rs);
                    Book book = bookRowMapper(rs, author, category, codeLocation);
                    allBook.add(book);
                }
                return Optional.of(allBook);
            }
        } catch (SQLException throwables) {
            System.out.println("Error when connect database");
        }
        return Optional.empty();
    }

    public Optional<List<Book>> searchByCategory(String c) {
        Connect connect = new Connect();
        try {
            try (Connection con = connect.connectDB();
                 PreparedStatement stmt = con.prepareStatement("select * from Book\n" +
                         "inner join Author on Book.authorID = Author.authorID \n" +
                         "inner join Category on Book.categoryID = Category.categoryID\n" +
                         "inner join CodeLocation on Book.codeLocation = CodeLocation.code where Category.categoryName like ? ")) {
                List<Book> allBook = new ArrayList<>();
                stmt.setString(1, c + "%");
                ResultSet rs = stmt.executeQuery();
                while ((rs.next())) {
                    Author author = authorRowMapper(rs);
                    Category category = categoryRowMapper(rs);
                    CodeLocation codeLocation = codeRowMapper(rs);
                    Book book = bookRowMapper(rs, author, category, codeLocation);
                    allBook.add(book);
                }
                return Optional.ofNullable(allBook);
            }
        } catch (SQLException throwables) {
            System.out.println("Error when connect database");
        }
        return Optional.empty();
    }

    public Book bookRowMapper(ResultSet rs, Author author, Category category, CodeLocation codeLocation) throws SQLException {

            Book book = new Book(rs.getInt("bookID"), rs.getInt("price"), rs.getInt("numberPage"),
                    rs.getString("nameBook"), rs.getString("bookStatus"), rs.getString("language"), author, category,
                    codeLocation, rs.getDate("dateBuy").toLocalDate());
            return book;
    }

    public Author authorRowMapper(ResultSet rs) throws SQLException {
        Author author = new Author(rs.getInt("authorID"), rs.getString("authorName"), rs.getString("authorDetail"));
        return author;
    }

    public Category categoryRowMapper(ResultSet rs) throws SQLException {
        Category category = new Category(rs.getInt("categoryID"), rs.getString("categoryName"),
                rs.getString("categoryDetail"));
        return category;
    }

    public CodeLocation codeRowMapper(ResultSet rs) throws SQLException {
        CodeLocation codeLocation = new CodeLocation(rs.getString("code"), rs.getString("codeDetail"));
        return codeLocation;
    }

}
