package by.mishastoma.libraryweb.model.dao;

import by.mishastoma.libraryweb.exception.DaoException;
import by.mishastoma.libraryweb.model.entity.Book;

public interface BookDao extends BaseDao<Book> {
    boolean addNewLibraryItem(long id) throws DaoException;
    boolean updateLibraryItem(long itemId, Long userId) throws DaoException;
    boolean associateBookWithAuthor (long bookId, long authorId) throws DaoException;
    boolean associateBookWithGenre (long bookId, long genreId) throws DaoException;
}
