import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Author {
    private int authorID;
    private String authorName, authorDetail;

    public Author(String authorName, String authorDetail) {
        this.authorName = authorName;
        this.authorDetail = authorDetail;
    }

    public int getAuthorID() {
        return authorID;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorDetail() {
        return authorDetail;
    }

    public void setAuthorDetail(String authorDetail) {
        this.authorDetail = authorDetail;
    }

    public Author getAuthor() throws SQLException {
        Connect connect = new Connect();
        try (Connection con = connect.connectDB();
        PreparedStatement stmt = con.prepareStatement("Select * from `Author` where authorName = ? " +
                "and authorDetail = ?");
        PreparedStatement stmt1 = con.prepareStatement("Insert into `Author` (authorName, authorDetail) " +
                "values (?, ?)");) {
            stmt.setString(1, this.authorName);
            stmt.setString(2, this.authorDetail);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                this.authorID = rs.getInt("authorID");
            } else {
                stmt1.setString(1, this.authorName);
                stmt1.setString(2, this.authorDetail);
                stmt1.executeUpdate();
                rs = stmt.executeQuery();
                if (rs.next()) {
                    this.authorID = rs.getInt("authorID");
                }
            }
        }
        return this;
    }
}
