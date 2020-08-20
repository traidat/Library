package Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class Category {
    private int categoryID;
    private String categoryName, categoryDetail;

    public Category(int categoryID, String categoryName, String categoryDetail) {
        this.categoryID = categoryID;
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

}
