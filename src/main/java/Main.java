import java.sql.*;
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
        String options = "sign(U)p L)ogin Q)uit";
        while (!isQuit) {
            System.out.println(options);
            String temp = scanner.nextLine();
            switch (temp.toLowerCase()) {
                case "u": {
                    currentAccount = signUp();
                    options = "S)earch C)heck-out Q)uit";
                } break;
                case "l": {
                    currentAccount = logIn();
                    if (currentAccount.getAccountType().equals("Librarian")) {
                        options = "S)earch C)heck-out Show B)orrowed book R)eturn book F)ull book " +
                                "A)dd book Q)uit";
                    } else {
                        options = "S)earch C)heck-out Show B)orrowed book R)eturn book F)ull book Q)uit";
                    }
                } break;
                case "a": {
                    addBook();
                    showAllBook();
                } break;
                case  "s" : {
                    searchBook();
                } break;
                case "f": {
                    showAllBook();
                } break;
                case "c": {
                    checkoutBook();
                } break;
                case "b": {
                    borrowedBook();
                } break;
                case "r": {
                    returnBook();
                } break;
                case "q": {
                    isQuit = true;
                } break;
            }
        }
    }

    private static void returnBook() throws SQLException {
        borrowedBook();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Fill in id book: ");
        int bookID = Integer.parseInt(scanner.nextLine());

        List<LendingBook> lendedBook = currentAccount.getOwner().getBookLending();
        for (int i = 0; i < lendedBook.size(); i++) {
            if (lendedBook.get(i).getBookID() == bookID) {
                lendedBook.get(i).setBookStatus("Returned");
                lendedBook.get(i).updateBookStatus();
                lendedBook.get(i).updateReturnDate();
                lendedBook.remove(i);
            }
        }
        borrowedBook();
    }

    private static void borrowedBook() throws SQLException {
        List<LendingBook> borrowedBooks = currentAccount.getOwner().getBookLending();
        if (borrowedBooks != null) {
            for (LendingBook i : borrowedBooks) {
                i.showLendingBook();
            }
        }
    }

    private static void checkoutBook() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Fill in id book: ");
        int bookID = Integer.parseInt(scanner.nextLine());
        LocalDate lendingDate = LocalDate.now();

        LendingBook lendingBook = new LendingBook(bookID,"Lended", currentAccount.getUsername(),
                lendingDate, lendingDate.plusDays(10));
        lendingBook.addLendingBookToDB();
        lendingBook.updateBookStatus();
        List<LendingBook> lendedBook = currentAccount.getOwner().getBookLending();
        lendedBook.add(lendingBook);
        currentAccount.getOwner().setBookLending(lendedBook);
        borrowedBook();
    }

    private static void searchBook() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1) Search by name 2) Search by author 3) Search by category");
        String temp = scanner.nextLine();
        switch (temp) {
            case "1": {
                System.out.print("Book name: ");
                String name = scanner.nextLine();
                currentAccount.getOwner().searchByName(name);

            } break;
            case "2": {
                System.out.print("Author name: ");
                String author = scanner.nextLine();
                currentAccount.getOwner().searchByAuthor(author);
            } break;
            case "3": {
                System.out.print("Select category F)iction R)omantic T)hriller: ");
                String category = scanner.nextLine();
                currentAccount.getOwner().searchByCategory(category);
            }
        }
    }


    private static void addBook() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Name book: ");
        String nameBook = scanner.nextLine();
        System.out.print("Page number: ");
        int numberPage = Integer.parseInt(scanner.nextLine());
        System.out.print("Price: ");
        int price = Integer.parseInt(scanner.nextLine());
        System.out.print("Language: ");
        String language = scanner.nextLine();
        System.out.print("Author name: ");
        String authorName = scanner.nextLine();
        System.out.print("Author detail: ");
        String authorDetail = scanner.nextLine();
        Author author = new Author(authorName, authorDetail);
        author = author.getAuthor();
        System.out.print("Select category F)iction R)omantic T)hriller: ");
        String category = scanner.nextLine();
        Category category1 = new Category();
        category1 = category1.getCategory(category);
        System.out.print("Code Location: ");
        String code = scanner.nextLine();
        System.out.print("Code Location detail: ");
        String codeDetail = scanner.nextLine();
        CodeLocation codeLocation = new CodeLocation(code, codeDetail);
        codeLocation = codeLocation.getCodeLocation();
        System.out.print("Date buy: ");
        String dateBuy = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dateBuy, formatter);

        Book newBook = new Book(price, numberPage, nameBook, "Available",
                language, author, category1, codeLocation, localDate);
        newBook.addBookToDB();
    }

    private static Account logIn() throws SQLException {
        boolean isLogin = false;
        while (!isLogin) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();
            Account account = new Account(username, password);
            account = account.checkAccount();
            if (account == null) {
                System.out.println("Invalid username or password");
            } else {
                System.out.println("Login success");
                isLogin = true;
                return account;
            }

        }
        return null;
    }

    private static Account signUp() throws SQLException {
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

        Person person = new Person(name, phoneNumber, email, address);
        person.addPersonToDB();

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
        Account account = new Account(person, username, password, "Active", "Member");
        account.addAccountToDB();
        return account;
    }

    private static void showAllBook() throws SQLException {
        Connect connect = new Connect();
        try (Connection con = connect.connectDB();
        PreparedStatement stmt = con.prepareStatement("Select * from `Book` where bookStatus = ?");
        PreparedStatement stmt1 = con.prepareStatement("Select * from `Author` where authorID = ?");) {
            stmt.setString(1, "Available");
            ResultSet rs = stmt.executeQuery();
            while ((rs.next())) {
                int id = rs.getInt("authorID");
                stmt1.setInt(1, id);
                ResultSet rs1 = stmt1.executeQuery();
                while (rs1.next()) {
                    System.out.println("Book " + rs.getString("bookID") + ": " + rs.getString("nameBook") +
                            " by " + rs1.getString("authorName"));
                }
            }
        }
    }


}
