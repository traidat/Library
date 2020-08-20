package PersonTest;

import DAO.PersonDAO;
import Service.PersonService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;

@RunWith(MockitoJUnitRunner.class)
public class PersonTest {

    @Mock
    private PersonDAO personDAO;

    @InjectMocks
    private PersonService personService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test() throws SQLException {
        Mockito.when(personDAO.addPersonToDB("the gioi", "01231", "ga@gmail.com", "Ha Noi")).thenReturn(true);
        Assert.assertEquals(true, personService.addPersonToDB("the gioi", "01231", "ga@gmail.com", "Ha Noi"));
    }


}
