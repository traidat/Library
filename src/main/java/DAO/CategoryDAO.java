package DAO;

import Entity.*;

import java.sql.*;
import java.util.Optional;

public class CategoryDAO {

    public Optional<Category> getCategory(String category)  {
        Connect connect = new Connect();
        try {
            try (Connection con = connect.connectDB();) {
                if (category.equals("f")) {
                    try (PreparedStatement stmt = con.prepareStatement("Select * from `Category` where categoryName = ?")) {
                        stmt.setString(1, "Fiction");
                        ResultSet rs = stmt.executeQuery();
                        while (rs.next()) {
                            Category category1 = new Category(rs.getInt("categoryID"), rs.getString("categoryName"),
                                    rs.getString("categoryDetail"));
                            return Optional.ofNullable(category1);
                        }
                    }
                } else if (category.equals("r")) {
                    try (PreparedStatement stmt = con.prepareStatement("Select * from `Category` where categoryName = ?");) {
                        stmt.setString(1, "Romantic");
                        ResultSet rs = stmt.executeQuery();
                        while (rs.next()) {
                            Category category1 = new Category(rs.getInt("categoryID"), rs.getString("categoryName"),
                                    rs.getString("categoryDetail"));
                            return Optional.ofNullable(category1);
                        }
                    }
                } else {
                    try (PreparedStatement stmt = con.prepareStatement("Select * from `Category` where categoryName = ?");) {
                        stmt.setString(1, "Thriller");
                        ResultSet rs = stmt.executeQuery();
                        while (rs.next()) {
                            Category category1 = new Category(rs.getInt("categoryID"), rs.getString("categoryName"),
                                    rs.getString("categoryDetail"));
                            return Optional.ofNullable(category1);
                        }
                    }
                }
            }
        } catch (SQLException throwables) {
            System.out.println("Error when connect database");
        }
        return Optional.empty();
    }
}
