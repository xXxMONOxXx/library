package by.mishastoma.libraryweb.model.dao.impl;

import by.mishastoma.libraryweb.model.dao.TableColumn;
import by.mishastoma.libraryweb.model.dao.UserDao;
import by.mishastoma.libraryweb.model.entity.User;
import by.mishastoma.libraryweb.exception.DaoException;
import by.mishastoma.libraryweb.controller.pool.ConnectionPool;
import by.mishastoma.libraryweb.model.mapper.CustomRowMapper;
import by.mishastoma.libraryweb.model.mapper.impl.UserMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private static final Logger logger = LogManager.getLogger();

    private static final String SELECT_USER_BY_LOGIN_AND_PASSWORD = """
            SELECT id, login, password, first_name, last_name, birthdate, is_blocked, email, role, days_balance
            FROM users WHERE login = ? AND password = ? """;

    private static final String SELECT_USER_BY_ID = """
            SELECT id, login, password, first_name, last_name, birthdate, is_blocked, email, role, days_balance
            FROM users WHERE id = ?""";

    private static final String SELECT_USER_ID_BY_LOGIN = """
            SELECT id FROM users WHERE login = ? """;

    private String SELECT_USER_BY_LOGIN = """
            SELECT id FROM users WHERE login=?""";

    private String SELECT_USER_BY_EMAIL = """
            SELECT id FROM users WHERE email=?""";

    private static final String SELECT_ALL_USERS = """
            SELECT * FROM users """;

    private static final String ADD_NEW_USER = """
            INSERT INTO users (login, password, first_name, last_name, email, birthdate)
            VALUES(?, ?, ?, ?, ?, ?)""";

    private static final String UPDATE_USERS_ROLE = """
            UPDATE users SET role = ? WHERE id = ?""";

    private static final String UPDATE_BALANCE_BY_USERS_ID = """
            UPDATE users SET days_balance = ? WHERE id = ?""";

    private static final String SELECT_USERS_BALANCE_BY_ID = """
            SELECT days_balance FROM users WHERE id = ?""";

    private static final String UPDATE_USER_STATE_BY_ID = """
            UPDATE users SET is_blocked = ? WHERE id = ?
            """;

    private static final String UPDATE_PASSWORD = """
            UPDATE users SET password = ? WHERE id = ?""";

    private static final String SELECT_USERS_PASSWORD = """
            SELECT password FROM users WHERE id = ?""";

    private static UserDaoImpl instance = new UserDaoImpl();

    private UserDaoImpl() {

    }

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean insert(User user) throws DaoException {
        if (emailExists(user.getEmail()) || loginExists(user.getLogin())) {
            return false;
        }
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_NEW_USER)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstname());
            statement.setString(4, user.getLastname());
            statement.setString(5, user.getEmail());
            statement.setDate(6, Date.valueOf(user.getBirthdate()));
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
    public boolean delete(long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CustomRowMapper<User> mapper = UserMapper.getInstance();
                Optional<User> user = mapper.map(resultSet);
                user.ifPresent(users::add);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return users;
    }

    @Override
    public boolean update(User user) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<User> signIn(String login, String password) throws DaoException {
        Optional<User> optionalUser = Optional.empty();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_LOGIN_AND_PASSWORD)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UserMapper mapper = UserMapper.getInstance();
                optionalUser = mapper.map(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return optionalUser;
    }

    @Override
    public boolean loginExists(String login) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean emailExists(String email) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_EMAIL)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public long getIdByLogin(String login) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_ID_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? resultSet.getLong(TableColumn.ID) : -1;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<User> getUserById(long id) throws DaoException {
        Optional<User> optionalUser = Optional.empty();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UserMapper mapper = UserMapper.getInstance();
                optionalUser = mapper.map(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return optionalUser;
    }

    @Override
    public int getUsersBalance(long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USERS_BALANCE_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return -1;
    }

    @Override
    public boolean updateUsersBalance(long id, int balance) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BALANCE_BY_USERS_ID)) {
            statement.setInt(1, balance);
            statement.setLong(2, id);
            if (statement.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return false;
    }

    @Override
    public boolean changeUserState(long id, boolean isBlocked) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_STATE_BY_ID)) {
            statement.setBoolean(1, isBlocked);
            statement.setLong(2, id);
            if (statement.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return false;
    }

    @Override
    public boolean changeUsersPassword(long id, String password) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PASSWORD)) {
            statement.setString(1, password);
            statement.setLong(2, id);
            if (statement.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return false;
    }

    @Override
    public String getUsersPassword(long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USERS_PASSWORD)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? resultSet.getString(TableColumn.PASSWORD) : null;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean changeUsersRole(long id, String role) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_ROLE)) {
            statement.setString(1, role);
            statement.setLong(2, id);
            if (statement.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return false;
    }
}
