package Entity;

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

    public CodeLocation() {
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

}
