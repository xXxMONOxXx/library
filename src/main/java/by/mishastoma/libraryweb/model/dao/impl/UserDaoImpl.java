package by.mishastoma.libraryweb.model.dao.impl;

import by.mishastoma.libraryweb.model.dao.BaseDao;
import by.mishastoma.libraryweb.model.dao.TableColumn;
import by.mishastoma.libraryweb.model.dao.UserDao;
import by.mishastoma.libraryweb.model.entity.User;
import by.mishastoma.libraryweb.model.entity.UserRole;
import by.mishastoma.libraryweb.exception.DaoException;
import by.mishastoma.libraryweb.controller.pool.ConnectionPool;
import by.mishastoma.libraryweb.model.mapper.impl.UserMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private static final Logger logger = LogManager.getLogger();

    private static final String SELECT_USER_BY_LOGIN_AND_PASSWORD = """
            SELECT id, login, password, first_name, last_name, birthdate, is_blocked, email, role
            FROM users WHERE login = ? AND password = ? """;

    private String SELECT_USER_BY_LOGIN = """
            SELECT id FROM users WHERE login=?""";

    private String SELECT_USER_BY_EMAIL = """
            SELECT id FROM users WHERE email=?""";

    private static final String SELECT_PASSWORD_BY_EMAIL = """
            SELECT password FROM users WHERE email = ? """;

    private static final String SELECT_ALL_USERS = """
            SELECT * FROM users """; // todo remove "*"

    private static final String ADD_NEW_USER = """
            INSERT INTO users (login, password, first_name, last_name, email, birthdate)
            VALUES(?, ?, ?, ?, ?, ?)""";

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
            statement.setString(6, user.getBirthdate().toString());
            if(statement.executeUpdate()==0){
                return false;
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return true;
    }

    @Override
    public boolean delete(User user) throws DaoException {
        return false;
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UserRole role = UserRole.valueOf(resultSet.getString(TableColumn.ROLE));
                User user = new User.Builder(resultSet.getLong(TableColumn.ID)).
                        withLogin(resultSet.getString(TableColumn.LOGIN)).
                        withRole(role).
                        withStatus(resultSet.getBoolean(TableColumn.IS_BLOCKED)).
                        build();
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return users;
    }

    @Override
    public User update(User user) throws DaoException {
        return null;
    }

    @Override
    public Optional<User> signIn(String login, String password) throws DaoException {
        // todo add new sign in methods (email)
        Optional<User> optionalUser = Optional.empty();
        try(Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_LOGIN_AND_PASSWORD)){
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                UserMapper mapper = new UserMapper();
                optionalUser = mapper.map(resultSet);
            }
        }
        catch (SQLException e){
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
}