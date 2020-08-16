import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class Category {
    private int categoryID;
    private String categoryName, categoryDetail;

    public Category(String categoryName, String categoryDetail) {
        this.categoryName = categoryName;
        this.categoryDetail = categoryDetail;
    }

    public Category() {
    }

    public int getCategoryID() {
        return categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getGetCategoryDetail() {
        return categoryDetail;
    }

    public void setGetCategoryDetail(String getCategoryDetail) {
        this.categoryDetail = getCategoryDetail;
    }

    public Category getCategory(String category) throws SQLException {
        Connect connect = new Connect();
        try (Connection con = connect.connectDB();) {
            if (category.equals("f")) {
                try (PreparedStatement stmt = con.prepareStatement("Select * from `Category` where categoryName = ?");) {
                    stmt.setString(1, "Fiction");
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        this.categoryID = rs.getInt("categoryID");
                        this.categoryName = rs.getString("categoryName");
                        this.categoryDetail = rs.getString("categoryDetail");
                    }
                }
            } else if (category.equals("r")) {
                try (PreparedStatement stmt = con.prepareStatement("Select * from `Category` where categoryName = ?");) {
                    stmt.setString(1, "Romantic");
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        this.categoryID = rs.getInt("categoryID");
                        this.categoryName = rs.getString("categoryName");
                        this.categoryDetail = rs.getString("categoryDetail");
                    }
                }
            } else {
                try (PreparedStatement stmt = con.prepareStatement("Select * from `Category` where categoryName = ?");) {
                    stmt.setString(1, "Thriller");
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        this.categoryID = rs.getInt("categoryID");
                        this.categoryName = rs.getString("categoryName");
                        this.categoryDetail = rs.getString("categoryDetail");
                    }
                }
            }
        }
        return this;
    }
}
