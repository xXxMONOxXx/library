package by.mishastoma.libraryweb.service.impl;

import by.mishastoma.libraryweb.exception.ServiceException;
import by.mishastoma.libraryweb.controller.pool.ConnectionPool;
import by.mishastoma.libraryweb.service.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    private String SELECT_USER_BY_LOGIN= """
            SELECT id FROM users WHERE login=?""";

    private String SELECT_USER_BY_EMAIL= """
            SELECT id FROM users WHERE email=?""";

    @Override
    public boolean loginExists(String login) throws ServiceException {
        try(Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
            //todo
        }
    }

    @Override
    public boolean emailExists(String email) throws ServiceException{
        try(Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_EMAIL)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
            //todo
        }
    }
}
