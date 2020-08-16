import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CodeLocation {
    private String code, codeDetail;

    public CodeLocation(String code, String codeDetail) {
        this.code = code;
        this.codeDetail = codeDetail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeDetail() {
        return codeDetail;
    }

    public void setCodeDetail(String codeDetail) {
        this.codeDetail = codeDetail;
    }

    public CodeLocation getCodeLocation() throws SQLException {
        Connect connect = new Connect();
        try (Connection con = connect.connectDB();
             PreparedStatement stmt1 = con.prepareStatement("Insert into `CodeLocation` (code, codeDetail) " +
                     "values (?, ?)");) {
            stmt1.setString(1, this.code);
            stmt1.setString(2, this.codeDetail);
            stmt1.executeUpdate();
        }
        return this;
    }
}
