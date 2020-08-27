package Entity;

import Service.BookService;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class LendingBook {
    private int bookID;
    private String username;
    private BookStatus.Status bookStatus;
    private LocalDate lendingDate, dueDate, returnDate;

    public LendingBook(int bookID, BookStatus.Status bookStatus, String username, LocalDate lendingDate, LocalDate dueDate) {
        this.bookID = bookID;
        this.bookStatus = bookStatus;
        this.username = username;
        this.lendingDate = lendingDate;
        this.dueDate = dueDate;
    }

    public int getBookID() {
        return bookID;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BookStatus.Status getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(BookStatus.Status bookStatus) {
        this.bookStatus = bookStatus;
    }

    public LocalDate getLendingDate() {
        return lendingDate;
    }

    public void setLendingDate(LocalDate lendingDate) {
        this.lendingDate = lendingDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public void showLendingBook()  {
        BookService bookService = new BookService();
        Optional<Book> optionalBook = bookService.getBook(bookID);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            System.out.println("Book " + this.bookID + ": " + book.getNameBook() + " by " + book.getAuthor().getAuthorName() +
                    " " + this.lendingDate + " " + this.dueDate);
        }

    }

    public void updateReturnDate() throws SQLException {

    }
}
