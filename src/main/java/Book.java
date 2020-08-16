import java.sql.*;
import java.time.LocalDate;

public class Book {
    private int bookID, price, numberPage;
    private String nameBook, bookStatus, language;
    private Author author;
    private Category category;
    private CodeLocation codeLocation;
    private LocalDate date;

    public Book(int price, int numberPage, String nameBook, String bookStatus,
                String language, Author author, Category category, CodeLocation codeLocation, LocalDate date) {
        this.price = price;
        this.numberPage = numberPage;
        this.nameBook = nameBook;
        this.bookStatus = bookStatus;
        this.language = language;
        this.author = author;
        this.category = category;
        this.codeLocation = codeLocation;
        this.date = date;
    }

    public void addBookToDB() throws SQLException {
        Connect connect = new Connect();
        try (Connection con = connect.connectDB();
             PreparedStatement stmt = con.prepareStatement("Insert into `Book` (nameBook, bookStatus, codeLocation," +
                     " authorID, categoryID, price, language, numberPage, dateBuy) " +
                     "values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
             PreparedStatement stmt1 = con.prepareStatement("Select * from `Book` where nameBook = ? " +
                     "and authorID = ?")) {
            stmt.setString(1, this.nameBook);
            stmt.setString(2, this.bookStatus);
            stmt.setString(3, this.codeLocation.getCode());
            stmt.setInt(4, this.author.getAuthorID());
            stmt.setInt(5, this.category.getCategoryID());
            stmt.setInt(6, this.price);
            stmt.setString(7, this.language);
            stmt.setInt(8, this.numberPage);
            stmt.setDate(9, Date.valueOf(date));
            stmt.executeUpdate();
            stmt1.setString(1, this.nameBook);
            stmt1.setInt(2, this.author.getAuthorID());
            ResultSet rs = stmt1.executeQuery();
            while (rs.next()) {
                int bookID = rs.getInt("bookID");
            }
        }
    }

}
