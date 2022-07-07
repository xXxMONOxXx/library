package by.mishastoma.libraryweb.model.dao.impl;

import by.mishastoma.libraryweb.controller.pool.ConnectionPool;
import by.mishastoma.libraryweb.exception.DaoException;
import by.mishastoma.libraryweb.model.dao.BookDao;
import by.mishastoma.libraryweb.model.entity.Author;
import by.mishastoma.libraryweb.model.entity.Book;
import by.mishastoma.libraryweb.model.entity.Genre;
import by.mishastoma.libraryweb.model.mapper.CustomRowMapper;
import by.mishastoma.libraryweb.model.mapper.impl.BookMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    private static final String FREE_LIB_ITEM = """
            UPDATE library_items SET user_id = NULL WHERE id = ? """;

    private static final String COUNT_FREE_LIBRARY_ITEMS_BY_ID = """
            SELECT COUNT(id) FROM library_items WHERE book_id = ? AND user_id IS NULL """;

    private static final String SELECT_BOOK_BY_ID = """
            SELECT id, name, info, release_date, age_limitations, cover_photo FROM books WHERE id = ?""";

    private static final String SELECT_AUTHORS_IDS_BY_BOOK_ID = """
            SELECT author_id FROM books_authors WHERE book_id = ?""";

    private static final String SELECT_GENRES_IDS_BY_BOOK_ID = """
            SELECT genre_id FROM books_genres WHERE book_id = ?""";

    private static final String SELECT_BOOKS_ID_WHERE_AUTHOR_ID = """
            SELECT book_id FROM books_authors WHERE author_id = ?""";

    private static final String SELECT_BOOKS_ID_WHERE_USER_ID = """
            SELECT book_id FROM library_items WHERE user_id = ?""";

    private static final String SELECT_FREE_LIB_ITEMS_WITH_BOOK_ID = """
            SELECT id FROM library_items WHERE book_id = ? AND user_id IS NULL""";

    private static final String SET_LIB_ITEM_TO_USER = """
            UPDATE library_items SET user_id = ? WHERE id = ?""";

    private static final String SELECT_FREE_LIB_ITEMS_WITH_BOOK_ID_AND_USER_ID = """
            SELECT id FROM library_items WHERE book_id = ? AND user_id = ?""";

    private static final String SELECT_ALL_BOOKS = """
            SELECT * FROM books """; // todo remove "*"

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
            for (Genre genre : book.getGenres()) {
                associateBookWithGenre(bookId, genre.getId());
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
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BOOKS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CustomRowMapper<Book> mapper = BookMapper.getInstance();
                Optional<Book> book = mapper.map(resultSet);
                book.ifPresent(books::add);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return books;
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
    public boolean freeLibraryItem(long itemId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FREE_LIB_ITEM)) {
            statement.setLong(1, itemId);
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

    @Override
    public Optional<Book> getBookById(long bookId) throws DaoException {
        Optional<Book> optionalBook = Optional.empty();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BOOK_BY_ID)) {
            statement.setLong(1, bookId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                CustomRowMapper<Book> mapper = BookMapper.getInstance();
                optionalBook = mapper.map(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return optionalBook;
    }

    @Override
    public Integer getBooksQuantity(long bookId) throws DaoException {
        int quantity = 0;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT_FREE_LIBRARY_ITEMS_BY_ID)) {
            statement.setLong(1, bookId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                quantity = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return quantity;
    }

    @Override
    public List<Long> getBooksAuthorsIds(long bookId) throws DaoException {
        List<Long> ids = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_AUTHORS_IDS_BY_BOOK_ID)) {
            statement.setLong(1, bookId);
            ResultSet resultSet = statement.executeQuery();
            int index = 1;
            while (resultSet.next()) {
                ids.add(resultSet.getLong(index));
                index++;
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return ids;
    }

    @Override
    public List<Long> getBooksGenresIds(long bookId) throws DaoException {
        List<Long> ids = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_GENRES_IDS_BY_BOOK_ID)) {
            statement.setLong(1, bookId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ids.add(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return ids;
    }

    @Override
    public List<Long> getAllBooksIdsWithAuthor(long authorId) throws DaoException {
        List<Long> ids = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BOOKS_ID_WHERE_AUTHOR_ID)) {
            statement.setLong(1, authorId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ids.add(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return ids;
    }

    @Override
    public List<Long> getUsersBooksIds(long userId) throws DaoException {
        List<Long> ids = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BOOKS_ID_WHERE_USER_ID)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ids.add(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return ids;
    }

    @Override
    public long getFreeLibItem(long bookId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_FREE_LIB_ITEMS_WITH_BOOK_ID)) {
            statement.setLong(1, bookId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return -1;
    }

    @Override
    public boolean setLibItemToUser(long libItemId, long userId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SET_LIB_ITEM_TO_USER)) {
            statement.setLong(1, userId);
            statement.setLong(2, libItemId);
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
    public long getItemId(long bookId, long userId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_FREE_LIB_ITEMS_WITH_BOOK_ID_AND_USER_ID)) {
            statement.setLong(1, bookId);
            statement.setLong(2, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return -1;
    }
}
