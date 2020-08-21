package Service;

import DAO.*;
import Entity.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LendingBookService {
    private BookDAO bookDAO = new BookDAO();
    private LendingBookDAO lendingBookDAO = new LendingBookDAO();

    public boolean lending(int id, Account account) {
        Optional<Book> book = bookDAO.getBook(id);
        boolean isLend = false;
        if (book.isPresent()) {
            isLend = lendingBookDAO.addLendingBook(id, account);
        }
        return isLend;
    }

    public boolean updateStatus(LendingBook lendingBook) {
        if (lendingBook.getBookStatus().equals("Lended")) {
            return lendingBookDAO.updateStatus(lendingBook);
        } else {
            return lendingBookDAO.updateAvailable(lendingBook);
        }

    }

    public List<LendingBook> getLendedBook(String username) {
        if (lendingBookDAO.getListLendingBook(username).isPresent()) {
            return lendingBookDAO.getListLendingBook(username).get();
        } else {
            return new ArrayList<>();
        }
    }

    public boolean updateReturnDate(LendingBook lendingBook) {
        return lendingBookDAO.updateReturnDate(lendingBook);
    }

}
