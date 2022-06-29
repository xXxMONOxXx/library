package by.mishastoma.libraryweb.model.dao;

import by.mishastoma.libraryweb.exception.DaoException;
import by.mishastoma.libraryweb.model.entity.Author;

public interface AuthorDao extends BaseDao<Author> {
    long getIdByAuthorsName(String firstname, String lastname) throws DaoException;
}
