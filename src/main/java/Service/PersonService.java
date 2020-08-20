package Service;

import DAO.*;
import Entity.*;

import java.sql.SQLException;
import java.util.Optional;

public class PersonService {
    private PersonDAO personDAO = new PersonDAO();

    public boolean addPersonToDB(String name, String phoneNumber, String email, String address) {
        return personDAO.addPersonToDB(name, phoneNumber, email, address);
    }

    public Optional<Person> getPerson(String email) {
        if (personDAO.getPerson(email).isPresent()) {
            return personDAO.getPerson(email);
        } else {
            return Optional.empty();
        }

    }

    public Optional<Person> getPerson(int id) {
        if (personDAO.getPerson(id).isPresent()) {
            return personDAO.getPerson(id);
        } else {
            return Optional.empty();
        }
    }

}
