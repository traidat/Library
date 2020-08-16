import java.util.List;

public class Librarian extends Person{

    public Librarian(String name, String phoneNumber, String email, String address) {
        super(name, phoneNumber, email, address);
    }

    public Librarian(int personID, String name, String phoneNumber, String email, String address) {
        super(personID, name, phoneNumber, email, address);
    }

}
