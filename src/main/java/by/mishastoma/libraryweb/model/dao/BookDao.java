package by.mishastoma.libraryweb.model.dao;

import by.mishastoma.libraryweb.exception.DaoException;
import by.mishastoma.libraryweb.model.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao extends BaseDao<Book> {

    boolean addNewLibraryItem(long id) throws DaoException;

    boolean freeLibraryItem(long itemId) throws DaoException;

    boolean associateBookWithAuthor(long bookId, long authorId) throws DaoException;

    boolean associateBookWithGenre(long bookId, long genreId) throws DaoException;

    Optional<Book> getBookById(long bookId) throws DaoException;

    Integer getBooksFreeQuantity(long bookId) throws DaoException;

    Integer getBooksQuantity(long bookId) throws DaoException;

    List<Long> getBooksAuthorsIds(long bookId) throws DaoException;

    List<Long> getBooksGenresIds(long bookId) throws DaoException;

    List<Long> getAllBooksIdsWithAuthor(long authorId) throws DaoException;

    List<Long> getUsersBooksIds(long userId) throws DaoException;

    long getFreeLibItem(long bookId) throws DaoException;

    boolean setLibItemToUser(long libItemId, long userId) throws DaoException;

    long getItemId(long bookId, long userId) throws DaoException;

    boolean updateWithoutCoverPhoto(Book book) throws DaoException;

    boolean deleteBooksGenres(long bookId) throws DaoException;

    boolean deleteBooksAuthors(long bookId) throws DaoException;

    boolean deleteBooksLibItems(long bookId) throws DaoException;

    boolean deleteAuthorFromBooks(long authorId) throws DaoException;

    boolean deleteGenreFromBooks(long genreId) throws DaoException;

    int countNumberOfBooks() throws DaoException;

    List<Book> getAll(int offSet, int amount) throws DaoException;

    int countBooksWithNameLike(String name) throws DaoException;

    List<Book> getBooksWithLikeName(String name, int offset, int amount) throws DaoException;
}
