import java.sql.*;
import java.time.LocalDate;

public class LendingBook {
    private int bookID;
    private String bookStatus, username;
    private LocalDate lendingDate, dueDate, returnDate;

    public LendingBook(int bookID, String bookStatus, String username, LocalDate lendingDate, LocalDate dueDate) {
        this.bookID = bookID;
        this.bookStatus = bookStatus;
        this.username = username;
        this.lendingDate = lendingDate;
        this.dueDate = dueDate;
    }

    public int getBookID() {
        return bookID;
    }

    public String getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getLendingDate() {
        return lendingDate;
    }

    public void setLendingDate(LocalDate lendingDate) {
        this.lendingDate = lendingDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public void addLendingBookToDB() throws SQLException {
        Connect connect = new Connect();
        try (Connection con = connect.connectDB();
             PreparedStatement stmt = con.prepareStatement("Insert into `LendingBook` (bookID, username, lendingDate" +
                     ", dueDate, bookStatus) " +
                     "values (?, ?, ?, ?, ?)");) {
            stmt.setInt(1, this.bookID);
            stmt.setString(2, this.username);
            stmt.setDate(3, Date.valueOf(this.lendingDate));
            stmt.setDate(4, Date.valueOf(this.dueDate));
            stmt.setString(5, this.bookStatus);
            stmt.executeUpdate();
        }
    }

    public void showLendingBook() throws SQLException {
        Connect connect = new Connect();
        try (Connection con = connect.connectDB();
             PreparedStatement stmt = con.prepareStatement("Select * from `Book` where bookID = ?")) {
            stmt.setInt(1, this.bookID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Book " + this.bookID + ": " + rs.getString("nameBook") + " " + this.lendingDate
                                    + " " + this.dueDate);
            }
        }
    }
    public void updateBookStatus() throws SQLException {
        if (this.bookStatus.equals("Lended")) {
            Connect connect = new Connect();
            try (Connection con = connect.connectDB();
                 PreparedStatement stmt = con.prepareStatement("Update `Book` set bookStatus = ? where bookID = ?")) {
                stmt.setInt(2, this.bookID);
                stmt.setString(1, "Not available");
                stmt.executeUpdate();
            }
        } else {
            Connect connect = new Connect();
            try (Connection con = connect.connectDB();
                 PreparedStatement stmt = con.prepareStatement("Update `Book` set bookStatus = ? where bookID = ?")) {
                stmt.setInt(2, this.bookID);
                stmt.setString(1, "Available");
                stmt.executeUpdate();
            }
        }
    }

    public void updateReturnDate() throws SQLException {
        Connect connect = new Connect();
        LocalDate returnDate = LocalDate.now();
        try (Connection con = connect.connectDB();
             PreparedStatement stmt = con.prepareStatement("Update `LendingBook` set returnDate = ? where bookID = ?")) {
            stmt.setInt(2, this.bookID);
            stmt.setDate(1, Date.valueOf(returnDate));
            stmt.executeUpdate();
        }
    }
}
