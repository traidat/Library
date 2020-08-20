package DAO;

import Entity.*;

import java.sql.*;
import java.util.Optional;

public class CodeDAO {

    public Optional<CodeLocation> addCode(String code, String codeDetail)  {
        Connect connect = new Connect();
        try {
            try (Connection con = connect.connectDB();
                 PreparedStatement stmt1 = con.prepareStatement("Insert into `CodeLocation` (code, codeDetail) " +
                         "values (?, ?)");) {
                stmt1.setString(1, code);
                stmt1.setString(2, codeDetail);
                int idAdd = stmt1.executeUpdate();
                if (idAdd > 0) {
                    CodeLocation codeLocation = new CodeLocation(code, codeDetail);
                    return Optional.ofNullable(codeLocation);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }
}
