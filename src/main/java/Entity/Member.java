package Entity;

import java.util.List;

public class Member extends Account {

    public Member(Person owner, String username, String password, String accountStatus, String accountType) {
        super(owner, username, password, accountStatus, accountType);
    }

    public Member() {
    }

    public Member(String username, String password) {
        super(username, password);
    }
}
