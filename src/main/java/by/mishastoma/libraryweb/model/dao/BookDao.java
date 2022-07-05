package by.mishastoma.libraryweb.model.dao;

import by.mishastoma.libraryweb.exception.DaoException;
import by.mishastoma.libraryweb.model.entity.Author;
import by.mishastoma.libraryweb.model.entity.Book;
import by.mishastoma.libraryweb.model.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface BookDao extends BaseDao<Book> {
    boolean addNewLibraryItem(long id) throws DaoException;
    boolean updateLibraryItem(long itemId, Long userId) throws DaoException;
    boolean associateBookWithAuthor (long bookId, long authorId) throws DaoException;
    boolean associateBookWithGenre (long bookId, long genreId) throws DaoException;
    Optional<Book> getBookById(long bookId) throws DaoException;
    Integer getBooksQuantity(long bookId) throws DaoException;
    List<Long> getBooksAuthorsIds(long bookId) throws DaoException;
    List<Long> getBooksGenresIds(long bookId) throws DaoException;
}
