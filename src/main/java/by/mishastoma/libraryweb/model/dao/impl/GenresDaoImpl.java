package by.mishastoma.libraryweb.model.dao.impl;

import by.mishastoma.libraryweb.model.dao.BaseDao;
import by.mishastoma.libraryweb.model.entity.Genre;
import by.mishastoma.libraryweb.exception.DaoException;

import java.util.List;

public class GenresDaoImpl implements BaseDao<Genre> {

    @Override
    public boolean insert(Genre genre) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Genre genre) throws DaoException {
        return false;
    }

    @Override
    public List<Genre> findAll() throws DaoException {
        return null;
    }

    @Override
    public Genre update(Genre genre) throws DaoException {
        return null;
    }
}
