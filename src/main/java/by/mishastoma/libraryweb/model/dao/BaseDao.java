package by.mishastoma.libraryweb.model.dao;

import by.mishastoma.libraryweb.exception.DaoException;

import java.util.List;

public interface BaseDao<T> {
    boolean insert(T t) throws DaoException;

    boolean delete(T t) throws DaoException;

    List<T> findAll() throws DaoException;

    T update(T t) throws DaoException;
}
