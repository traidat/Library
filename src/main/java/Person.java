import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Person {
    private List<LendingBook> bookLending = new ArrayList<>();
    private String name, phoneNumber, email, address;
    private int personID;

    public Person(String name, String phoneNumber, String email, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    public Person(int personID, String name, String phoneNumber, String email, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.personID = personID;
    }

    public List<LendingBook> getBookLending() {
        return bookLending;
    }

    public void setBookLending(List<LendingBook> bookLending) {
        this.bookLending = bookLending;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPersonID() {
        return personID;
    }

    public void addPersonToDB() throws SQLException {
        Connect connect = new Connect();
        try (Connection con = connect.connectDB();
        PreparedStatement stmt = con.prepareStatement("Insert into `Person` (name, phoneNumber, email, address) " +
                "values (?, ?, ?, ?)");
             PreparedStatement stmt1 = con.prepareStatement("Select * from `Person` where email = ?")) {
            stmt.setString(1, this.name);
            stmt.setString(2, this.phoneNumber);
            stmt.setString(3, this.email);
            stmt.setString(4, this.address);
            stmt.executeUpdate();
            stmt1.setString(1, this.email);
            ResultSet rs = stmt1.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("personID");
                this.personID = id;
            }
        }
    }

    public void searchByName(String nameBook) throws SQLException {
        Connect connect = new Connect();
        try (Connection con = connect.connectDB();
             PreparedStatement stmt = con.prepareStatement("Select * from `Book` where nameBook like ?");
             PreparedStatement stmt1 = con.prepareStatement("Select * from `Author` where authorID = ?")){
            stmt.setString(1, "%" + nameBook + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("authorID");
                stmt1.setInt(1, id);
                ResultSet rs1 = stmt1.executeQuery();
                while (rs1.next()) {
                    System.out.println("Book " + rs.getString("bookID") + " : " + rs.getString("nameBook") +
                            " by " + rs1.getString("authorName"));
                }
            }
        }
    }



    public void searchByAuthor(String author) throws SQLException {
        Connect connect = new Connect();
        try (Connection con = connect.connectDB();
             PreparedStatement stmt = con.prepareStatement("Select * from `Author` where authorName like ?");
             PreparedStatement stmt1 = con.prepareStatement("Select * from `Book` where authorID = ?")){
            stmt.setString(1, "%" + author + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("authorID");
                stmt1.setInt(1, id);
                ResultSet rs1 = stmt1.executeQuery();
                while (rs1.next()) {
                    System.out.println("Book " + rs1.getString("bookID") + " : " + rs1.getString("nameBook") +
                            " by " + rs.getString("authorName"));
                }
            }
        }
    }

    public void searchByCategory(String category) throws SQLException {
        Connect connect = new Connect();
        try (Connection con = connect.connectDB();
             PreparedStatement stmt = con.prepareStatement("Select * from `Category` where categoryName like ?");
             PreparedStatement stmt1 = con.prepareStatement("Select * from `Book` where categoryID = ?");
             PreparedStatement stmt2 = con.prepareStatement("Select * from `Author` where authorID = ?")){
            stmt.setString(1,category + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("categoryID");
                stmt1.setInt(1, id);
                ResultSet rs1 = stmt1.executeQuery();
                while (rs1.next()) {
                    int authorID = rs1.getInt("authorID");
                    stmt2.setInt(1, authorID);
                    ResultSet rs2 = stmt2.executeQuery();
                    while (rs2.next()) {
                        System.out.println("Book " + rs1.getString("bookID") + " : " + rs1.getString("nameBook") +
                                " by " + rs2.getString("authorName"));
                    }
                }
            }
        }
    }
}
