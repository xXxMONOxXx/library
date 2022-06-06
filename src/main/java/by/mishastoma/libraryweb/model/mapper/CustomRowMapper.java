package by.mishastoma.libraryweb.model.mapper;

import by.mishastoma.libraryweb.model.entity.AbstractEntity;
import by.mishastoma.libraryweb.exception.DaoException;

import java.sql.ResultSet;
import java.util.Optional;

public interface CustomRowMapper<E extends AbstractEntity> {
    Optional<E> map(ResultSet resultSet) throws DaoException;
}
