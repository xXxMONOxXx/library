package by.mishastoma.libraryweb.model.mapper.impl;

import by.mishastoma.libraryweb.model.entity.UserRole;
import by.mishastoma.libraryweb.model.mapper.CustomRowMapper;
import by.mishastoma.libraryweb.model.dao.TableColumn;
import by.mishastoma.libraryweb.model.entity.User;
import by.mishastoma.libraryweb.exception.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class UserMapper implements CustomRowMapper<User> {

    private static UserMapper instance;

    public static UserMapper getInstance() {
        if (instance == null) {
            instance = new UserMapper();
        }
        return instance;
    }

    private UserMapper() {

    }

    @Override
    public Optional<User> map(ResultSet resultSet) throws DaoException {
        User user;
        Optional<User> optionalUser;
        try {
            LocalDate birthdate = LocalDate.parse(resultSet.getString(TableColumn.BIRTHDATE));
            user = new User.Builder(resultSet.getLong(TableColumn.ID)).
                    withLogin(resultSet.getString(TableColumn.LOGIN)).
                    withPassword(resultSet.getString(TableColumn.PASSWORD)).
                    withFirstName(resultSet.getString(TableColumn.FIRST_NAME)).
                    withLastName(resultSet.getString(TableColumn.LAST_NAME)).
                    withRole(UserRole.valueOf(resultSet.getString(TableColumn.ROLE))).
                    withEmail(resultSet.getString(TableColumn.EMAIL)).
                    withBirthdate(birthdate).
                    withStatus(resultSet.getBoolean(TableColumn.IS_BLOCKED)).
                    withBalance(resultSet.getInt(TableColumn.BALANCE)).
                    build();
            optionalUser = Optional.of(user);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return optionalUser;
    }
}
