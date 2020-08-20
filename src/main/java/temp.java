import DAO.Connect;

import java.sql.*;
import java.time.LocalDate;

public class temp {
    public static void main(String[] args) throws SQLException {
        Connect connect = new Connect();
        Connection con = connect.connectDB();
        PreparedStatement stmt = con.prepareStatement("Insert into `Author` (authorName, authorDetail) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, "Hay");
        stmt.setString(2, "Hay");
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        while (rs.next()) {
            System.out.println(rs.getInt(1));
        }
    }
}
