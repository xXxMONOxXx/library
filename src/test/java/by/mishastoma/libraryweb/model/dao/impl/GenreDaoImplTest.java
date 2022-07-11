package by.mishastoma.libraryweb.model.dao.impl;

import by.mishastoma.libraryweb.exception.DaoException;
import by.mishastoma.libraryweb.model.dao.GenreDao;
import by.mishastoma.libraryweb.model.entity.Genre;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GenreDaoImplTest extends AbstractDaoTest{

    private static final String name = "genre";
    private static final long id = 1;
    private static Genre expectedGenre;

    private final GenreDao genreDao = GenreDaoImpl.getInstance();

    @BeforeMethod
    public void setUp() {
        expectedGenre = new Genre(id, name);
    }

    @Test
    public void insertTest() throws DaoException {
        assertTrue(genreDao.insert(expectedGenre));
    }


    @Test (priority = 1)
    public void findAllTest() throws DaoException {
        List<Genre> genreList = genreDao.findAll();
        assertEquals(genreList.get(0).getId(),expectedGenre.getId());
    }

    @Test (priority = 2)
    public void getIdByAuthorsNameTest() throws DaoException {
        long actualId = genreDao.getIdByName(name);
        assertEquals(actualId, id);
    }

    @Test (priority = 3)
    public void getAuthorByIdTest() throws DaoException{
        Optional<Genre> actualGenre = genreDao.getGenreById(id);
        assertEquals(actualGenre.get().getId(),expectedGenre.getId());
    }

    @Test (priority = 4)
    public void updateTest() throws DaoException {
        assertTrue(genreDao.update(expectedGenre));
    }

    @Test (priority = 5)
    public void deleteTest() throws DaoException {
        assertTrue(genreDao.delete(id));
    }
}
