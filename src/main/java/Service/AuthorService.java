package Service;

import DAO.*;
import Entity.Author;

import java.util.Optional;

public class AuthorService {
    private AuthorDAO authorDAO = new AuthorDAO();

    public Optional<Author> addAuthor(String authorName, String authorDetail) {
        if (authorDAO.getAuthor(authorName, authorDetail).isPresent()) {
            return authorDAO.getAuthor(authorName, authorDetail);
        } else if (authorDAO.addAuthor(authorName, authorDetail)) {
            return authorDAO.getAuthor(authorName, authorDetail);
        } else {
            return Optional.empty();
        }
    }
}
