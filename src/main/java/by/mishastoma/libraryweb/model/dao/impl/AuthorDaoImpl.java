package by.mishastoma.libraryweb.model.dao.impl;

import by.mishastoma.libraryweb.controller.pool.ConnectionPool;
import by.mishastoma.libraryweb.exception.DaoException;
import by.mishastoma.libraryweb.model.dao.AuthorDao;
import by.mishastoma.libraryweb.model.dao.BaseDao;
import by.mishastoma.libraryweb.model.dao.TableColumn;
import by.mishastoma.libraryweb.model.entity.Author;
import by.mishastoma.libraryweb.model.mapper.impl.AuthorMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorDaoImpl implements AuthorDao {

    private static final Logger logger = LogManager.getLogger();

    private static final String SELECT_AUTHOR_BY_FIRSTNAME_AND_LASTNAME = """
            SELECT id FROM authors WHERE first_name= ? AND last_name=?""";

    private static final String ADD_NEW_AUTHOR = """
            INSERT INTO authors (first_name, last_name, bio)
            VALUES(?, ?, ?)""";

    private static final String SELECT_ALL_AUTHORS = """
            SELECT * FROM authors """;
    //todo add pagination


    private static AuthorDaoImpl instance = new AuthorDaoImpl();

    private AuthorDaoImpl() {

    }

    public static AuthorDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean insert(Author author) throws DaoException {
        if (getIdByAuthorsName(author.getFirstname(), author.getLastname()) != -1) {
            return false;
        }
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_NEW_AUTHOR)) {
            statement.setString(1, author.getFirstname());
            statement.setString(2, author.getLastname());
            statement.setString(3, author.getBiography());
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
    public boolean delete(Author author) throws DaoException {
        throw new UnsupportedOperationException(); //todo
    }

    @Override
    public List<Author> findAll() throws DaoException {
        List<Author> authors = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_AUTHORS)) {
            ResultSet resultSet = statement.executeQuery();
            AuthorMapper mapper = AuthorMapper.getInstance();
            while (resultSet.next()) {
                Optional<Author> optionalAuthor = mapper.map(resultSet);
                optionalAuthor.ifPresent(authors::add);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return authors;
    }

    @Override
    public Author update(Author author) throws DaoException {
        throw new UnsupportedOperationException(); //todo
    }

    @Override
    public long getIdByAuthorsName(String firstname, String lastname) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_AUTHOR_BY_FIRSTNAME_AND_LASTNAME)) {
            statement.setString(1, firstname);
            statement.setString(2, lastname);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? resultSet.getLong(TableColumn.ID) : -1;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }
}