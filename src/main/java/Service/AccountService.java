package Service;

import DAO.*;
import Entity.*;

import java.util.Optional;

public class AccountService {
    private AccountDAO accountDAO = new AccountDAO();

    public boolean isExistAccount(String username, String password) {
        return accountDAO.getAccount(username, password).isPresent();
    }

    public boolean addAccount(Person person, String username, String password, String accountStatus, String accountType) {
        return accountDAO.addAccount(person, username, password, accountStatus, accountType);
    }

    public Optional<Account> getAccount(String username) {
        if (accountDAO.getAccount(username).isPresent()) {
            return accountDAO.getAccount(username);
        } else {
            return Optional.empty();
        }

    }
}
