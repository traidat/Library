package Entity;

import java.sql.*;
import java.time.LocalDate;

public class Book {
    private int bookID, price, numberPage;
    private String nameBook, language;
    private BookStatus.Status bookStatus;
    private Author author;
    private Category category;
    private CodeLocation codeLocation;
    private LocalDate date;

    public Book(int price, int numberPage, String nameBook, BookStatus.Status bookStatus,
                String language, Author author, Category category, CodeLocation codeLocation, LocalDate date) {
        this.price = price;
        this.numberPage = numberPage;
        this.nameBook = nameBook;
        this.bookStatus = bookStatus;
        this.language = language;
        this.author = author;
        this.category = category;
        this.codeLocation = codeLocation;
        this.date = date;
    }

    public Book(int bookID, int price, int numberPage, String nameBook, BookStatus.Status bookStatus, String language,
                Author author, Category category, CodeLocation codeLocation, LocalDate date) {
        this.bookID = bookID;
        this.price = price;
        this.numberPage = numberPage;
        this.nameBook = nameBook;
        this.bookStatus = bookStatus;
        this.language = language;
        this.author = author;
        this.category = category;
        this.codeLocation = codeLocation;
        this.date = date;
    }

    public Book() {
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNumberPage() {
        return numberPage;
    }

    public void setNumberPage(int numberPage) {
        this.numberPage = numberPage;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }


    public BookStatus.Status getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(BookStatus.Status bookStatus) {
        this.bookStatus = bookStatus;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public CodeLocation getCodeLocation() {
        return codeLocation;
    }

    public void setCodeLocation(CodeLocation codeLocation) {
        this.codeLocation = codeLocation;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void showBook() {
        System.out.printf("Book %d:%s by %s, category: %s, langauge: %s, status: %s\n", this.bookID, this.nameBook, this.author.getAuthorName(),
                this.category.getCategoryName(), this.language, this.bookStatus);
    }
}
