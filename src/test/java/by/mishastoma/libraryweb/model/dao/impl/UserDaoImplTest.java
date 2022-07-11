package by.mishastoma.libraryweb.model.dao.impl;

import by.mishastoma.libraryweb.exception.DaoException;
import by.mishastoma.libraryweb.model.dao.UserDao;
import by.mishastoma.libraryweb.model.entity.User;
import by.mishastoma.libraryweb.model.entity.UserRole;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class UserDaoImplTest extends AbstractDaoTest{
    private static final long id = 1;
    private static final String password = "12345";
    private static final String firstname = "firstname";
    private static final String lastname = "lastname";
    private static final String email = "mail@mail.com";
    private static final String birthdate = "1997-02-14";
    private static final String login = "login";
    private static final boolean isBlocked = false;
    private static final String role = "USER";
    private static final String ADMIN_ROLE = "ADMIN";
    private static final int NEW_BALANCE = 10;
    private static final int daysBalance = 0;
    private static User expectedUser;

    private final UserDao userDao = UserDaoImpl.getInstance();

    @BeforeMethod
    public void setUp() {
        expectedUser = new User.Builder(id).
                withLogin(login).
                withFirstName(firstname).
                withLastName(lastname).
                withEmail(email).
                withPassword(password).
                withBirthdate(LocalDate.parse(birthdate)).
                withRole(UserRole.USER).
                build();
    }

    @Test
    public void insertTest() throws DaoException {
        assertTrue(userDao.insert(expectedUser));
    }

    @Test (priority = 1)
    public void findAllTest() throws DaoException{
        List<User> usersList = userDao.findAll();
        assertEquals(usersList.get(0).getId(),expectedUser.getId());
    }

    @Test (priority = 1)
    public void signInTest() throws DaoException{
        Optional<User> optionalUser = userDao.signIn(login, password);
        assertEquals(optionalUser.get().getId(), expectedUser.getId());
    }

    @Test (priority = 1)
    public void loginExistsTest() throws DaoException{
        assertTrue(userDao.loginExists(login));
    }

    @Test (priority = 1)
    public void emailExistsTest() throws DaoException{
        assertTrue(userDao.emailExists(email));
    }

    @Test (priority = 1)
    public void getIdByLoginTest() throws DaoException{
        long actualId = userDao.getIdByLogin(login);
        assertEquals(actualId, id);
    }

    @Test (priority = 1)
    public void getUserByIdTest() throws DaoException{
        Optional<User> optionalUser = userDao.getUserById(id);
        assertEquals(optionalUser.get().getId(), id);
    }

    @Test (priority = 1)
    public void getUsersBalanceTest() throws DaoException{
        int actualBalance = userDao.getUsersBalance(id);
        assertEquals(actualBalance, daysBalance);
    }

    @Test (priority = 2)
    public void updateUsersBalanceTest() throws DaoException{
        assertTrue(userDao.updateUsersBalance(id, NEW_BALANCE));
    }

    @Test (priority = 2)
    public void changeUserStateTest() throws DaoException{
        assertTrue(userDao.changeUserState(id, true));
    }

    @Test (priority = 1)
    public void getUsersPasswordTest() throws DaoException{
        String actualPassword = userDao.getUsersPassword(id);
        assertEquals(actualPassword, password);
    }

    @Test (priority = 2)
    public void changeUsersPasswordTest() throws DaoException{
        assertTrue(userDao.changeUsersPassword(id, ADMIN_ROLE));
    }

    @Test (priority = 2)
    public void changeUsersRole() throws DaoException{
        assertTrue(userDao.changeUsersRole(id, ADMIN_ROLE));
    }

}
