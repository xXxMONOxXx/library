package by.mishastoma.libraryweb.dao.impl;

import by.mishastoma.libraryweb.dao.BaseDao;
import by.mishastoma.libraryweb.dao.UserDao;
import by.mishastoma.libraryweb.entity.User;
import by.mishastoma.libraryweb.exception.DaoException;
import by.mishastoma.libraryweb.exception.ServiceException;
import by.mishastoma.libraryweb.controller.pool.ConnectionPool;
import by.mishastoma.libraryweb.service.UserService;
import by.mishastoma.libraryweb.service.impl.UserServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl extends BaseDao<User> implements UserDao {

    private static final String SELECT_PASSWORD_BY_LOGIN = """
            SELECT password FROM users WHERE login = ? """;

    private static final String SELECT_PASSWORD_BY_EMAIL = """
            SELECT password FROM users WHERE email = ? """;

    private static final String SELECT_ALL_USERS= """
            SELECT * FROM users """;

    private static final String ADD_NEW_USER = """
            INSERT INTO users (login, password, first_name, last_name, email, birthdate)
            VALUES(?, ?, ?, ?, ?, ?)""";

    private static UserDaoImpl instance = new UserDaoImpl();

    private UserDaoImpl(){

    }

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean insert(User user) throws DaoException {
        UserService service = new UserServiceImpl();
        try {
            if(service.emailExists(user.getEmail()) || service.loginExists(user.getLogin())){
                return false;
            }
        } catch (ServiceException e) {
            e.printStackTrace();//todo
        }
        try(Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_USER)){
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstname());
            statement.setString(4, user.getLastname());
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getBirthdate().toString());
            statement.executeUpdate();
        }
        catch (SQLException e){
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
        List <User> users = null;
        try(Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS)){ // todo add new sign in methods (email)
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String login = resultSet.getString("login");
                String firstname = resultSet.getString("login");
                String lastname = resultSet.getString("login");
                String password = resultSet.getString("login");

                User user = new User(i, str);

                ll.add(user);
            }
        }
        catch (SQLException e){
            throw new DaoException(e);
        }
        return users
    }

    @Override
    public User update(User user) throws DaoException {
        return null;
    }

    @Override
    public boolean authenticate(String signInLogin, String password) throws DaoException {
        boolean match = false;
        try(Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_PASSWORD_BY_LOGIN)){ // todo add new sign in methods (email)
            statement.setString(1, signInLogin);
            ResultSet resultSet = statement.executeQuery();
            String passFromDb;
            if(resultSet.next()){
                passFromDb = resultSet.getString(1);
                match = passFromDb.equals(password);
            }
        }
        catch (SQLException e){
            throw new DaoException(e);
        }
        return match;
    }
}
