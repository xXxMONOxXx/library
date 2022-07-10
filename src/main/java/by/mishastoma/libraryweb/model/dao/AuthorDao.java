package by.mishastoma.libraryweb.model.dao;

import by.mishastoma.libraryweb.exception.DaoException;
import by.mishastoma.libraryweb.model.entity.Author;

import java.util.Optional;

public interface AuthorDao extends BaseDao<Author> {
    long getIdByAuthorsName(String firstname, String lastname) throws DaoException;

    Optional<Author> getAuthorById(long id) throws DaoException;
}
