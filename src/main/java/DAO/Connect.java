package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    public Connection connectDB()  {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/libraryDB", "root",
                    "Truong201075@");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
