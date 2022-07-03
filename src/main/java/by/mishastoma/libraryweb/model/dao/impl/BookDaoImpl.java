package by.mishastoma.libraryweb.model.dao.impl;

import by.mishastoma.libraryweb.controller.pool.ConnectionPool;
import by.mishastoma.libraryweb.exception.DaoException;
import by.mishastoma.libraryweb.model.dao.BookDao;
import by.mishastoma.libraryweb.model.entity.Author;
import by.mishastoma.libraryweb.model.entity.Book;
import by.mishastoma.libraryweb.model.entity.Genre;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

public class BookDaoImpl implements BookDao {

    private static final Logger logger = LogManager.getLogger();

    private static BookDaoImpl instance = new BookDaoImpl();

    private BookDaoImpl() {

    }

    private static final String ADD_NEW_BOOK = """
            INSERT INTO books (name, info, release_date, age_limitations, cover_photo)
            VALUES(?, ?, ?, ?, ?)""";

    private static final String ADD_LIB_ITEM = """
            INSERT INTO library_items (book_id)
            VALUES(?)""";

    private static final String ADD_BOOKS_AUTHOR = """
            INSERT INTO books_authors (author_id, book_id)
            VALUES (?, ?)""";

    private static final String ADD_BOOKS_GENRE = """
            INSERT INTO books_genres (genre_id, book_id)
            VALUES (?, ?)""";

    public static BookDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean insert(Book book) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_NEW_BOOK, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getName());
            statement.setString(2, book.getInfo());
            statement.setDate(3, Date.valueOf(book.getReleaseDate()));
            statement.setInt(4, book.getAgeLimitation());
            statement.setBlob(5, book.getCoverPhoto());
            if (statement.executeUpdate() == 0) {
                return false;
            }
            //todo use this in others DAOS to !!!
            ResultSet results = statement.getGeneratedKeys();
            results.next();
            long bookId = results.getLong(1);
            //
            book.setId(bookId);
            for (int i = 0; i < book.getQuantity(); i++) {
                addNewLibraryItem(bookId);
            }
            for (Author author : book.getAuthors()) {
                associateBookWithAuthor(bookId, author.getId());
            }
            for(Genre genre : book.getGenres()){
                associateBookWithGenre(bookId,genre.getId());
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return true;
    }

    @Override
    public boolean delete(Book book) throws DaoException {
        return false;
    }

    @Override
    public List<Book> findAll() throws DaoException {
        return null;
    }

    @Override
    public Book update(Book book) throws DaoException {
        return null;
    }

    @Override
    public boolean addNewLibraryItem(long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_LIB_ITEM)) {
            statement.setLong(1, id);
            if (statement.executeUpdate() == 0) {
                return false;
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return true;
    }

    @Override
    public boolean updateLibraryItem(long itemId, Long userId) throws DaoException {
        return false;
    }

    @Override
    public boolean associateBookWithAuthor(long bookId, long authorId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_BOOKS_AUTHOR)) {
            statement.setLong(1, authorId);
            statement.setLong(2, bookId);
            if (statement.executeUpdate() == 0) {
                return false;
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return true;
    }

    @Override
    public boolean associateBookWithGenre(long bookId, long genreId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_BOOKS_GENRE)) {
            statement.setLong(1, genreId);
            statement.setLong(2, bookId);
            if (statement.executeUpdate() == 0) {
                return false;
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return true;
    }
}
