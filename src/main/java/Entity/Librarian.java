package Entity;

import java.util.List;

public class Librarian extends Account {

    public Librarian(Person owner, String username, String password, String accountStatus, String accountType) {
        super(owner, username, password, accountStatus, accountType);
    }

    public Librarian() {
    }

    public Librarian(String username, String password) {
        super(username, password);
    }



}
