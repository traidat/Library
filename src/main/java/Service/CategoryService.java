package Service;

import DAO.*;
import Entity.Category;

import java.util.Optional;

public class CategoryService {
    private CategoryDAO categoryDAO = new CategoryDAO();

    public Optional<Category> getCategory(String category) {
        if (categoryDAO.getCategory(category).isPresent()) {
            return categoryDAO.getCategory(category);
        } else {
            return Optional.empty();
        }

    }
}
