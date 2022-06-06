package by.mishastoma.libraryweb.model.mapper.impl;

import by.mishastoma.libraryweb.model.mapper.CustomRowMapper;
import by.mishastoma.libraryweb.model.dao.TableColumn;
import by.mishastoma.libraryweb.model.entity.User;
import by.mishastoma.libraryweb.exception.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class UserMapper implements CustomRowMapper<User> {
    @Override
    public Optional<User> map(ResultSet resultSet) throws DaoException {
        User user;
        Optional<User> optionalUser;
        try{
            LocalDate birthdate = LocalDate.parse(resultSet.getString(TableColumn.BIRTHDATE));
            user = new User.Builder(resultSet.getLong(TableColumn.ID)).
                    withLogin(resultSet.getString(TableColumn.LOGIN)).
                    withPassword(resultSet.getString(TableColumn.PASSWORD)).
                    withFirstName(resultSet.getString(TableColumn.FIRST_NAME)).
                    withLastName(resultSet.getString(TableColumn.LAST_NAME)).
                    withEmail(resultSet.getString(TableColumn.EMAIL)).
                    withBirthdate(birthdate).
                    withStatus(resultSet.getBoolean(TableColumn.IS_BLOCKED)).
                    build();
            optionalUser = Optional.of(user);
        }
        catch (SQLException e){
            throw new DaoException(e);
        }

        return optionalUser;
    }
}
