package by.mishastoma.libraryweb.model.service;

import by.mishastoma.libraryweb.exception.ServiceException;
import by.mishastoma.libraryweb.model.entity.Book;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface BookService {
    List<Book> getAll() throws ServiceException;
    Optional<Book> addBook(Map<String, Object> bookMap, Set<String> invalids) throws ServiceException;
    Optional<Book> getBookById(long id) throws ServiceException;
    List<Book> getBooksByAuthorsId(long authorId) throws ServiceException;
    List<Book> getBooksUserHas(long userId) throws ServiceException;
    boolean addBookToUser (long userId, long bookId) throws ServiceException;
    boolean freeBookFromUser(long userId, long bookId) throws ServiceException;
    int getActualBooksQuantity(long id) throws ServiceException;
    boolean updateBook(Map<String, Object> bookMap, Set<String> invalids) throws ServiceException;
    boolean deleteBook (long bookId) throws ServiceException;
    int countNumberOfBooks() throws ServiceException;
    List<Book> getAmount(int offSet, int amount) throws ServiceException;
    List<Book> getBooksWithNameLike(String name, int offSet, int amount) throws ServiceException;
    int countBooksWithNameLike(String name) throws ServiceException;
}
