
import Entity.*;
import Service.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    static Account currentAccount = new Account();
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        boolean isQuit = false;
        String options = "sign(U)p Log I)n Q)uit";
        while (!isQuit) {
            System.out.println(options);
            String temp = scanner.nextLine();
            switch (temp.toLowerCase()) {
                // Dang ky
                case "u": {
                    Optional<Account> account = signUp();
                    if (account.isPresent()) {
                        currentAccount = account.get();
                    }
                    options = "S)earch C)heck-out Q)uit";
                } break;
                // Dang nhap
                case "i": {
                    Optional<Account> account = logIn();
                    if (account.isPresent()) {
                        currentAccount = account.get();
                        System.out.println("Welcome " + currentAccount.getOwner().getName());
                        if (currentAccount.getAccountType().equals("Librarian")) {
                            options = "S)earch L)end book B)orrowed book R)eturn book E)xtend" +
                                    " A)dd book M)odify book Q)uit";
                        } else {
                            options = "S)earch C)heck-out Show B)orrowed book R)eturn book E)xtend Q)uit";
                        }
                    }
                } break;
                // Them sach
                case "a": {
                    addBook();
                } break;
                // Tim sach
                case  "s" : {
                    searchBook();
                } break;
                // Gia han
                case "e": {
                    extend();
                } break;
                // Muon sach
                case "l": {
                    lendingBook();
                } break;
                // Xem sach da muon
                case "b": {
                    borrowedBook();
                } break;
                // Tra sach
                case "r": {
                    returnBook();
                } break;
                case "m": {
                    modifyBook();
                } break;
                case "q": {
                    isQuit = true;
                } break;
            }
        }
    }

    private static void extend() {
        borrowedBook();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Fill in id book you want extend: ");
        int bookID = Integer.parseInt(scanner.nextLine());

        LendingBookService lendingBookService = new LendingBookService();

        List<LendingBook> lendedBook = currentAccount.getOwner().getBookLending();
        for (int i = 0; i < lendedBook.size(); i++) {
            if (lendedBook.get(i).getBookID() == bookID && lendingBookService.updateStatus(lendedBook.get(i)) && lendingBookService.updateReturnDate(lendedBook.get(i))) {
                lendedBook.get(i).setBookStatus(BookStatus.Status.Returned);
                lendingBookService.updateStatus(lendedBook.get(i));
                lendingBookService.updateReturnDate(lendedBook.get(i));
                lendedBook.remove(i);
            }
        }
        currentAccount.getOwner().setBookLending(lendedBook);
        LocalDate lendingDate = LocalDate.now();

        LendingBook lendingBook = new LendingBook(bookID, BookStatus.Status.Lended, currentAccount.getUsername(),
                lendingDate, lendingDate.plusDays(10));
        boolean isLend = lendingBookService.lending(bookID, currentAccount);
        if (isLend) {
            System.out.println("Extend success");
            lendingBookService.updateStatus(lendingBook);
            lendedBook = currentAccount.getOwner().getBookLending();
            lendedBook.add(lendingBook);
            currentAccount.getOwner().setBookLending(lendedBook);
        } else {
            System.out.println("Extend failed");
        }
        borrowedBook();
    }

    private static void modifyBook()  {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Fill in id book: ");
        int bookID = Integer.parseInt(scanner.nextLine());
        BookService bookService = new BookService();
        Optional<Book> optionalBook = bookService.getBook(bookID);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.showBook();
            System.out.println("Please type all row:");
            System.out.print("Name book: ");
            String nameBook = scanner.nextLine();
            System.out.print("Page number: ");
            String temp = scanner.nextLine();
            int numberPage;
            if (temp != "") {
                numberPage = Integer.parseInt(temp);
            } else {
                numberPage = 0;
            }
            System.out.print("Price: ");
            temp = scanner.nextLine();
            int price;
            if (temp != "") {
                price = Integer.parseInt(temp);
            } else {
                price = 0;
            }
            System.out.print("Language: ");
            String language = scanner.nextLine();
            System.out.print("Author name: ");
            String authorName = scanner.nextLine();
            System.out.print("Author detail: ");
            String authorDetail = scanner.nextLine();
            AuthorService authorService = new AuthorService();
            Optional<Author> optionalAuthor = authorService.addAuthor(authorName, authorDetail);


            System.out.print("Select category F)iction R)omantic T)hriller: ");
            String category = scanner.nextLine();
            CategoryService categoryService = new CategoryService();
            Optional<Category> optionalCategory = categoryService.getCategory(category);

            System.out.print("Date buy: ");
            String dateBuy = scanner.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
            LocalDate localDate = LocalDate.parse(dateBuy, formatter);

            if (optionalCategory.isPresent() && optionalAuthor.isPresent()) {
                Category category1 = optionalCategory.get();
                Author author = optionalAuthor.get();
                if (bookService.modifyBook(bookID, price, numberPage, nameBook, "Available", language, author,
                        category1, localDate)) {
                    System.out.println("Modify book success");
                } else {
                    System.out.println("Modify book failed");
                }
            }
        }
    }

    private static void returnBook()  {
        borrowedBook();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Fill in id book you want to return: ");
        int bookID = Integer.parseInt(scanner.nextLine());

        LendingBookService lendingBookService = new LendingBookService();

        List<LendingBook> lendedBook = currentAccount.getOwner().getBookLending();
        for (int i = 0; i < lendedBook.size(); i++) {
            if (lendedBook.get(i).getBookID() == bookID && lendingBookService.updateStatus(lendedBook.get(i)) && lendingBookService.updateReturnDate(lendedBook.get(i))) {
                lendedBook.get(i).setBookStatus(BookStatus.Status.Returned);
                lendingBookService.updateStatus(lendedBook.get(i));
                lendingBookService.updateReturnDate(lendedBook.get(i));
                System.out.println("Return book id " + lendedBook.get(i).getBookID() + " success");
                lendedBook.remove(i);
            }
        }
        currentAccount.getOwner().setBookLending(lendedBook);
        borrowedBook();
    }

    private static void borrowedBook() {
        List<LendingBook> borrowedBooks = currentAccount.getOwner().getBookLending();
        if (borrowedBooks.size() > 0) {
            for (LendingBook i : borrowedBooks) {
                i.showLendingBook();
            }
        } else {
            System.out.println("You have not borrowed any book");
        }
    }

    private static void lendingBook() {
        if (currentAccount.getOwner().getBookLending().size() >= 5) {
            System.out.println("You can not lend more than 5 book");
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Fill in id book you want to lend: ");
            int bookID = Integer.parseInt(scanner.nextLine());
            LocalDate lendingDate = LocalDate.now();

            LendingBook lendingBook = new LendingBook(bookID, BookStatus.Status.Lended, currentAccount.getUsername(),
                    lendingDate, lendingDate.plusDays(10));
            LendingBookService lendingBookService = new LendingBookService();
            boolean isLend = lendingBookService.lending(bookID, currentAccount);
            if (isLend) {
                System.out.println("Lend success");
                lendingBookService.updateStatus(lendingBook);
                List<LendingBook> lendedBook = currentAccount.getOwner().getBookLending();
                lendedBook.add(lendingBook);
                currentAccount.getOwner().setBookLending(lendedBook);
                borrowedBook();
            } else {
                System.out.println("Lend failed");
            }
        }
    }

    private static void searchBook()  {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1) Search by name 2) Search by author 3) Search by category");
        String temp = scanner.nextLine();
        switch (temp) {
            case "1": {
                System.out.print("Book name: ");
                String name = scanner.nextLine();
                BookService bookService = new BookService();
                List<Book> books = bookService.searchByName(name);
                if (books.size() == 0) {
                    System.out.println("Can't find the right book");
                } else {
                    for (Book b : books) {
                        b.showBook();
                    }
                    lendingBook();
                }
            } break;
            case "2": {
                System.out.print("Author name: ");
                String author = scanner.nextLine();
                BookService bookService = new BookService();
                List<Book> books = bookService.searchByAuthor(author);
                if (books.size() == 0) {
                    System.out.println("Can't find the right book");
                } else {
                    for (Book b : books) {
                        b.showBook();
                    }
                    lendingBook();
                }
            } break;
            case "3": {
                System.out.print("Select category F)iction R)omantic T)hriller: ");
                String category = scanner.nextLine();
                BookService bookService = new BookService();
                List<Book> books = bookService.searchByCategory(category);
                if (books.size() == 0) {
                    System.out.println("Can't find the right book");
                } else {
                    for (Book b : books) {
                        b.showBook();
                    }
                    lendingBook();
                }
            }
        }
    }

    private static void addBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Name book: ");
        String nameBook = scanner.nextLine();
        System.out.print("Page number: ");
        String temp = scanner.nextLine();
        int numberPage;
        if (!temp.equals("")) {
            numberPage = Integer.parseInt(temp);
        } else {
            numberPage = 0;
        }
        System.out.print("Price: ");
        temp = scanner.nextLine();
        int price;
        if (!temp.equals("")) {
            price = Integer.parseInt(temp);
        } else {
            price = 0;
        }
        System.out.print("Language: ");
        String language = scanner.nextLine();

        boolean checkAuthor = false;
        Optional<Author> optionalAuthor = Optional.of(new Author());
        while (!checkAuthor) {
            System.out.print("Author name: ");
            String authorName = scanner.nextLine();
            System.out.print("Author detail: ");
            String authorDetail = scanner.nextLine();
            AuthorService authorService = new AuthorService();
            if (!authorName.equals("")) {
                optionalAuthor = authorService.addAuthor(authorName, authorDetail);
                if (optionalAuthor.isPresent()) {
                    checkAuthor = true;
                }
            } else {
                System.out.println("Author invalid");
            }
        }

        System.out.print("Select category F)iction R)omantic T)hriller: ");
        String category = scanner.nextLine();
        CategoryService categoryService = new CategoryService();
        Optional<Category> optionalCategory = categoryService.getCategory(category);

        Optional<CodeLocation> optionalCodeLocation = Optional.of(new CodeLocation());
        boolean checkCode = false;
        while (!checkCode) {
            System.out.print("Code Location: ");
            String code = scanner.nextLine();
            System.out.print("Code Location detail: ");
            String codeDetail = scanner.nextLine();
            CodeService codeService = new CodeService();
            optionalCodeLocation = codeService.addCode(code, codeDetail);
            if (optionalCodeLocation.isPresent()) {
                checkCode = true;
            }
        }

        System.out.print("Date buy: ");
        String dateBuy = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dateBuy, formatter);

        if (optionalCodeLocation.isPresent() && optionalCategory.isPresent() && optionalAuthor.isPresent()) {
            CodeLocation codeLocation  = optionalCodeLocation.get();
            Category category1 = optionalCategory.get();
            Author author = optionalAuthor.get();
            BookService bookService = new BookService();
            if (bookService.addBook(price, numberPage, nameBook, String.valueOf(BookStatus.Status.Available), language, author,
                    category1, codeLocation, localDate)) {
                System.out.println("Add book success");
            } else {
                System.out.println("Book already exist");
            }
        }
    }

    private static Optional<Account> logIn() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();
            AccountService accountService = new AccountService();
            boolean isExist = accountService.isExistAccount(username, password);
            if (!isExist) {
                System.out.println("Invalid username or password");
            } else {
                return accountService.getAccount(username);
            }
        }
    }

    private static Optional<Account> signUp() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Phone number: ");
        String phoneNumber = scanner.nextLine();
        boolean checkEmail = true;
        String email = "";
        while(checkEmail) {
            System.out.print("Email: ");
            email = scanner.nextLine();
            String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                System.out.println("Invalid email");
            } else {
                checkEmail = false;
            }
        }
        System.out.print("Address: ");
        String address = scanner.nextLine();

        PersonService personService = new PersonService();
        boolean isAdd = personService.addPersonToDB(name, phoneNumber, email, address);
        Optional<Person> optionalPerson = personService.getPerson(email);

        System.out.print("Username: ");
        String username = scanner.nextLine();
        boolean checkPassword = true;
        String password = "";
        String rePassword = "";
        while(checkPassword) {
            System.out.print("Password: ");
            password = scanner.nextLine();
            System.out.print("Password again: ");
            rePassword = scanner.nextLine();
            if (password.equals(rePassword)) {
                checkPassword = false;
            } else {
                System.out.println("Invalid password");
            }
        }
        if (isAdd && optionalPerson.isPresent()) {
            Person person = personService.getPerson(email).get();
            AccountService accountService = new AccountService();
            accountService.addAccount(person, username, password, "Active", "Member");
            return accountService.getAccount(username);
        } else {
            return Optional.empty();
        }

    }

//    private static void showAllBook() {
//        BookService bookService = new BookService();
//        List<Book> allBook = bookService.allBook();
//        if (allBook.size() > 0) {
//            for (Book b : allBook) {
//                if (b.getBookStatus().equals("Available")) {
//                    b.showBook();
//                }
//            }
//        }
//    }
}
