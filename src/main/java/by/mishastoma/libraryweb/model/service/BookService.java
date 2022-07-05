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
}
