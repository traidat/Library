package Service;

import DAO.*;
import Entity.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookService {
    private BookDAO bookDAO = new BookDAO();

    public boolean addBook(int price, int numberPage, String nameBook, String bookStatus, String language,
                           Author author, Category category, CodeLocation codeLocation, LocalDate date) {
        if (bookDAO.isBookExist(nameBook, author)) {
            return bookDAO.addBook(price, numberPage, nameBook, bookStatus, language, author, category, codeLocation, date);
        } else {
            return false;
        }
    }

    public List<Book> allBook() {
        if (bookDAO.allBook().isPresent()) {
            return bookDAO.allBook().get();
        } else {
            return new ArrayList<>();
        }
    }

    public Optional<Book> getBook(int id) {
        if (bookDAO.getBook(id).isPresent()) {
            return bookDAO.getBook(id);
        } else {
            return Optional.empty();
        }
    }

    public List<Book> searchByName(String name) {
        if (bookDAO.searchByName(name).isPresent()) {
            return bookDAO.searchByName(name).get();
        } else {
            return new ArrayList<>();
        }
    }

    public List<Book> searchByAuthor(String authorName) {
        if (bookDAO.searchByAuthor(authorName).isPresent()) {
            return bookDAO.searchByAuthor(authorName).get();
        } else {
            return new ArrayList<>();
        }
    }

    public List<Book> searchByCategory(String c) {
        if (bookDAO.searchByCategory(c).isPresent()) {
            return bookDAO.searchByCategory(c).get();
        } else {
            return new ArrayList<>();
        }
    }

    public boolean modifyBook(int bookID, int price, int numberPage, String nameBook, String bookStatus, String language,
                           Author author, Category category, LocalDate date) {
        return bookDAO.modifyBook(bookID, price, numberPage, nameBook, bookStatus, language, author, category, date);
    }


}
