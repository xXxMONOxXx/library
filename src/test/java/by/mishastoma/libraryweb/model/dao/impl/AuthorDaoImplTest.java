package by.mishastoma.libraryweb.model.dao.impl;

import by.mishastoma.libraryweb.exception.DaoException;
import by.mishastoma.libraryweb.model.dao.AuthorDao;
import by.mishastoma.libraryweb.model.entity.Author;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class AuthorDaoImplTest extends AbstractDaoTest {

    private static long id = 1;
    private static final String firstname = "Alex";
    private static final String lastname = "Pushkin";
    private static final String bio = "some bio";
    private static Author expectedAuthor;

    private final AuthorDao authorDao = AuthorDaoImpl.getInstance();

    @BeforeMethod
    public void setUp() {
        expectedAuthor = new Author(id, firstname, lastname, bio);
    }

    @Test
    public void insertTest() throws DaoException {
        assertTrue(authorDao.insert(expectedAuthor));
    }

    @Test (priority = 1)
    public void findAllTest() throws DaoException {
        List<Author> authorList = authorDao.findAll();
        assertEquals(authorList.get(0).getId(),expectedAuthor.getId());
    }

    @Test (priority = 2)
    public void getIdByAuthorsNameTest() throws DaoException {
        long actualId = authorDao.getIdByAuthorsName(firstname, lastname);
        assertEquals(actualId, id);
    }

    @Test (priority = 3)
    public void getAuthorByIdTest() throws DaoException{
        Optional<Author> actualAuthor = authorDao.getAuthorById(id);
        assertEquals(actualAuthor.get().getId(),expectedAuthor.getId());
    }

    @Test (priority = 4)
    public void updateTest() throws DaoException {
        assertTrue(authorDao.update(expectedAuthor));
    }

    @Test (priority = 5)
    public void deleteTest() throws DaoException {
        assertTrue(authorDao.delete(id));
    }
}
