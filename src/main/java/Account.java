import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private Person owner;
    private String username, password, accountStatus, accountType;

    public Account(Person owner, String username, String password, String accountStatus, String accountType) {
        this.owner = owner;
        this.username = username;
        this.password = password;
        this.accountStatus = accountStatus;
        this.accountType = accountType;
    }

    public Account() {
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void addAccountToDB() throws SQLException {
        LocalDate createdOn = LocalDate.now();

        Connect connect = new Connect();
        try (Connection con = connect.connectDB()) {
            PreparedStatement stmt = con.prepareStatement("Insert into `Account` (personID, username, password, " +
                    "accountStatus, createdOn, accountType) values (?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, this.owner.getPersonID());
            stmt.setString(2, this.username);
            stmt.setString(3, this.password);
            stmt.setString(4, this.accountStatus);
            stmt.setDate(5, Date.valueOf(createdOn));
            stmt.setString(6, this.accountType);
            stmt.executeUpdate();
        }
    }
    public List<LendingBook> getListLendingBook() throws SQLException {
        Connect connect = new Connect();
        try (Connection con = connect.connectDB();
             PreparedStatement stmt = con.prepareStatement("Select * from `LendingBook` where username = ? " +
                     "and bookStatus = ?")) {
            stmt.setString(1, this.username);
            stmt.setString(2, "Lended");
            ResultSet rs = stmt.executeQuery();
            List<LendingBook> lendingBooks = new ArrayList<>();
            while (rs.next()) {
                LocalDate lendingDate = rs.getDate("lendingDate").toLocalDate();
                LocalDate dueDate = rs.getDate("dueDate").toLocalDate();

                lendingBooks.add(new LendingBook(rs.getInt("bookID"), rs.getString("bookStatus"),
                        rs.getString("username"),
                        lendingDate, dueDate));
            }
            return lendingBooks;
        }
    }

    public Account checkAccount() throws SQLException {
        Connect connect = new Connect();
        try (Connection con = connect.connectDB();
             PreparedStatement stmt = con.prepareStatement("Select * from `Account` where username = ?");
             PreparedStatement stmt1 = con.prepareStatement("Select * from `Person` where personID = ?")) {
            stmt.setString(1, this.username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int personID = rs.getInt("personID");
                String password = rs.getString("password");
                String accountStatus = rs.getString("accountStatus");
                String accountType = rs.getString("accountType");
                if (password.equals(this.password)) {
                    stmt1.setInt(1, personID);
                    ResultSet rs1 = stmt1.executeQuery();
                    while (rs1.next()) {
                        String name = rs1.getString("name");
                        String phoneNumber = rs1.getString("phoneNumber");
                        String email = rs1.getString("email");
                        String address = rs1.getString("address");
                        if (accountType.equals("Librarian")) {
                            this.owner = new Librarian(personID, name, phoneNumber, email, address);
                        } else {
                            this.owner = new Member(personID, name, phoneNumber, email, address);
                        }
                        this.accountStatus = accountStatus;
                        this.accountType = accountType;
                    }
                    this.owner.setBookLending(getListLendingBook());
                    return this;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
    }
}
