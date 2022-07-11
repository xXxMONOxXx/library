package by.mishastoma.libraryweb.model.dao.impl;

import by.mishastoma.libraryweb.exception.DaoException;
import by.mishastoma.libraryweb.model.dao.BookDao;
import by.mishastoma.libraryweb.model.entity.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class BookDaoImplTest extends AbstractDaoTest {


    private static final String NEW_NAME = "new name";
    private static final String NAME_LIKE = "book";
    private static final int expectedCount = 1;

    private static final String name = "genre";
    private static final long genreId = 1;
    private static Genre expectedGenre;

    private static final long userId = 1;
    private static final String password = "12345";
    private static final String usersFirstname = "firstname";
    private static final String userLastname = "lastname";
    private static final String email = "mail@mail.com";
    private static final String birthdate = "1997-02-14";
    private static final String login = "login";
    private static final boolean isBlocked = false;
    private static final String role = "USER";
    private static final String ADMIN_ROLE = "ADMIN";
    private static final int NEW_BALANCE = 10;
    private static final int daysBalance = 0;
    private static User expectedUser;

    private static long authorId = 1;
    private static final String authorsFirstname = "Alex";
    private static final String authorsLastname = "Pushkin";
    private static final String bio = "some bio";
    private static Author expectedAuthor;

    private static final long bookId = 1;
    private static final String bookName = "book name";
    private static final String bookInfo = "book info";
    private static final String booksReleaseDate = "2000-12-12";
    private static final Integer ageLimitations = 9;
    private static final String quantity = "1";
    private static final InputStream coverPhoto = null;

    private static Book expectedBook;

    private static List<Author> authors = new ArrayList<>();
    private static List<Genre> genres = new ArrayList<>();

    private static final int expectedNumber = 1;

    private static BookDao bookDao = BookDaoImpl.getInstance();

    @BeforeMethod
    public void setUp() {
        expectedGenre = new Genre(genreId, name);
        genres.add(expectedGenre);
        expectedAuthor = new Author(authorId, authorsFirstname, authorsLastname, bio);
        authors.add(expectedAuthor);
        expectedUser = new User.Builder(userId).
                withLogin(login).
                withFirstName(usersFirstname).
                withLastName(userLastname).
                withEmail(email).
                withPassword(password).
                withBirthdate(LocalDate.parse(birthdate)).
                withRole(UserRole.USER).
                build();
        expectedBook = new Book.Builder(bookId).
                withName(bookName).
                withInfo(bookInfo).
                withReleaseDate(LocalDate.parse(booksReleaseDate)).
                withGenres(genres).
                withAuthors(authors).
                withAgeLimitations(ageLimitations).
                withCoverPhoto(coverPhoto).
                withQuantity(Integer.parseInt(quantity))
                .build();
    }

    @Test
    public void insertTest() throws DaoException {
        assertTrue(bookDao.insert(expectedBook));
    }

    @Test (priority = 1)
    public void findAllTest() throws DaoException {
        List<Book> actualBooks = bookDao.findAll();
        assertEquals(actualBooks.get(0).getId(), bookId);
    }

    @Test (priority = 1)
    public void getBookByIdTest() throws DaoException {
        Optional<Book> actualBook = bookDao.getBookById(bookId);
        assertEquals(actualBook.get().getId(), bookId);
    }

    @Test (priority = 1)
    public void getBooksFreeQuantityTest() throws DaoException {
        int actualFreeQuantity = bookDao.getBooksFreeQuantity(bookId);
        assertEquals(actualFreeQuantity, Integer.parseInt(quantity));
    }

    @Test (priority = 1)
    public void getBooksQuantityTest() throws DaoException {
        int actualQuantity = bookDao.getBooksQuantity(bookId);
        assertEquals(actualQuantity, Integer.parseInt(quantity));
    }

    @Test (priority = 1)
    public void getBooksAuthorsIdsTest() throws DaoException {
        List<Long> actualIds = bookDao.getBooksAuthorsIds(bookId);
        assertEquals(actualIds.get(0), authorId);
    }

    @Test (priority = 1)
    public void getBooksGenresIdsTest() throws DaoException {
        List<Long> actualIds = bookDao.getBooksGenresIds(bookId);
        assertEquals(actualIds.get(0), genreId);
    }

    @Test (priority = 1)
    public void countBooksWithNameLikeTest() throws DaoException {
        int actualCount = bookDao.countBooksWithNameLike(NAME_LIKE);
        assertEquals(actualCount, expectedCount);
    }

    @Test (priority = 3)
    public void updateTest() throws DaoException {
        expectedBook.setName(NEW_NAME);
        assertTrue(bookDao.update(expectedBook));
    }

    @Test(priority = 5)
    public void deleteTest() throws DaoException {
        assertTrue(bookDao.delete(bookId));
    }

}
