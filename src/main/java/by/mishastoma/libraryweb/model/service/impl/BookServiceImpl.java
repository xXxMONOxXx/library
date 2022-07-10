package by.mishastoma.libraryweb.model.service.impl;

import by.mishastoma.libraryweb.controller.ParameterName;
import by.mishastoma.libraryweb.exception.DaoException;
import by.mishastoma.libraryweb.exception.ServiceException;
import by.mishastoma.libraryweb.model.dao.AuthorDao;
import by.mishastoma.libraryweb.model.dao.BookDao;
import by.mishastoma.libraryweb.model.dao.GenreDao;
import by.mishastoma.libraryweb.model.dao.impl.AuthorDaoImpl;
import by.mishastoma.libraryweb.model.dao.impl.BookDaoImpl;
import by.mishastoma.libraryweb.model.dao.impl.GenreDaoImpl;
import by.mishastoma.libraryweb.model.entity.Author;
import by.mishastoma.libraryweb.model.entity.Book;
import by.mishastoma.libraryweb.model.entity.Genre;
import by.mishastoma.libraryweb.model.service.BookService;
import by.mishastoma.libraryweb.validator.BookValidator;
import by.mishastoma.libraryweb.validator.impl.BookValidatorImpl;
import com.mysql.cj.util.StringUtils;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BookServiceImpl implements BookService {

    private static BookServiceImpl instance = new BookServiceImpl();

    private BookServiceImpl() {

    }

    public static BookServiceImpl getInstance() {
        return instance;
    }


    @Override
    public List<Book> getAll() throws ServiceException {
        try {
            BookDao bookDao = BookDaoImpl.getInstance();
            return bookDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Book> addBook(Map<String, Object> bookMap, Set<String> invalids) throws ServiceException {
        Optional<Book> optionalBook = Optional.empty();
        if (isValidBook(bookMap, invalids)) {
            String[] authorsIds = (String[]) bookMap.get(ParameterName.BOOK_AUTHORS);
            List<Author> authors = null;
            if (authorsIds != null) {
                List<Long> authorsIdsLong = Stream.of(authorsIds).map(Long::valueOf).collect(Collectors.toList());
                authors = getAuthorsByIds(authorsIdsLong);
            }
            String[] genresIds = (String[]) bookMap.get(ParameterName.BOOK_GENRES);
            List<Genre> genres = null;
            if (genresIds != null) {
                List<Long> genresIdsLong = Stream.of(genresIds).map(Long::valueOf).collect(Collectors.toList());
                genres = getGenresByIds(genresIdsLong);
            }
            Part coverPhoto = (Part) bookMap.get(ParameterName.BOOK_COVER_PHOTO);
            String ageLimitationsStr = (String) bookMap.get(ParameterName.BOOK_AGE_LIMITATIONS);
            Integer ageLimitations;
            if(StringUtils.isEmptyOrWhitespaceOnly(ageLimitationsStr)){
                ageLimitations = null;
            }
            else{
                ageLimitations = Integer.parseInt(ageLimitationsStr);
            }
            try {
                Book book = new Book.Builder(-1).
                        withName((String) bookMap.get(ParameterName.BOOK_NAME)).
                        withInfo((String) bookMap.get(ParameterName.BOOK_INFO)).
                        withReleaseDate(LocalDate.parse((String) bookMap.get(ParameterName.BOOK_RELEASE_DATE))).
                        withGenres(genres).
                        withAuthors(authors).
                        withAgeLimitations(ageLimitations).
                        withCoverPhoto(coverPhoto.getInputStream()).
                        withQuantity(Integer.parseInt((String) bookMap.get(ParameterName.BOOK_QUANTITY)))
                        .build();
                BookDao bookDao = BookDaoImpl.getInstance();
                bookDao.insert(book);
                optionalBook = Optional.of(book);

            } catch (DaoException | IOException e) {
                throw new ServiceException(e);
            }
        }
        return optionalBook;
    }

    @Override
    public Optional<Book> getBookById(long id) throws ServiceException {
        Optional<Book> optionalBook = Optional.empty();
        try {
            BookDao dao = BookDaoImpl.getInstance();
            optionalBook = dao.getBookById(id);
            if (optionalBook.isPresent()) {
                long bookId = optionalBook.get().getId();
                int quantity = dao.getBooksFreeQuantity(bookId);
                optionalBook.get().setQuantity(quantity);
                optionalBook.get().setAuthors(getAuthorsByIds(dao.getBooksAuthorsIds(bookId)));
                optionalBook.get().setGenres(getGenresByIds(dao.getBooksGenresIds(bookId)));
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return optionalBook;
    }

    @Override
    public List<Book> getBooksByAuthorsId(long authorId) throws ServiceException {
        BookDao bookDao = BookDaoImpl.getInstance();
        try {
            return getBooksByIds(bookDao.getAllBooksIdsWithAuthor(authorId));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Book> getBooksUserHas(long userId) throws ServiceException {
        BookDao bookDao = BookDaoImpl.getInstance();
        try {
            return getBooksByIds(bookDao.getUsersBooksIds(userId));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public boolean addBookToUser(long userId, long bookId) throws ServiceException {
        BookDao bookDao = BookDaoImpl.getInstance();
        try {
            long freeBookId = bookDao.getFreeLibItem(bookId);
            if (freeBookId == -1) {
                return false;
            }
            return bookDao.setLibItemToUser(freeBookId, userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean freeBookFromUser(long userId, long bookId) throws ServiceException {
        BookDao bookDao = BookDaoImpl.getInstance();
        try {
            long itemId = bookDao.getItemId(bookId, userId);
            if (itemId == -1) {
                return false;
            }
            return bookDao.freeLibraryItem(itemId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public int getActualBooksQuantity(long id) throws ServiceException {
        BookDao bookDao = BookDaoImpl.getInstance();
        try {
            return bookDao.getBooksQuantity(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateBook(Map<String, Object> bookMap, Set<String> invalids) throws ServiceException {

        if (isValidBook(bookMap, invalids)) {
            String[] authorsIds = (String[]) bookMap.get(ParameterName.BOOK_AUTHORS);
            List<Long> authorsIdsLong = Stream.of(authorsIds).map(Long::valueOf).collect(Collectors.toList());
            List<Author> authors = getAuthorsByIds(authorsIdsLong);

            String[] genresIds = (String[]) bookMap.get(ParameterName.BOOK_GENRES);
            List<Long> genresIdsLong = Stream.of(genresIds).map(Long::valueOf).collect(Collectors.toList());
            List<Genre> genres = getGenresByIds(genresIdsLong);

            try {
                BookDao bookDao = BookDaoImpl.getInstance();
                Book book = new Book.Builder(Long.parseLong((String) bookMap.get(ParameterName.BOOK_ID))).
                        withName((String) bookMap.get(ParameterName.BOOK_NAME)).
                        withInfo((String) bookMap.get(ParameterName.BOOK_INFO)).
                        withReleaseDate(LocalDate.parse((String) bookMap.get(ParameterName.BOOK_RELEASE_DATE))).
                        withGenres(genres).
                        withAuthors(authors).
                        withAgeLimitations(Integer.valueOf((String) bookMap.get(ParameterName.BOOK_AGE_LIMITATIONS))).
                        withQuantity(Integer.parseInt((String) bookMap.get(ParameterName.BOOK_QUANTITY)))
                        .build();
                if (!bookMap.containsKey(ParameterName.BOOK_COVER_PHOTO)) {
                    return bookDao.updateWithoutCoverPhoto(book);
                } else {
                    Part coverPhoto = (Part) bookMap.get(ParameterName.BOOK_COVER_PHOTO);
                    if (coverPhoto != null) {
                        book.setCoverPhoto(coverPhoto.getInputStream());
                    } else {
                        book.setCoverPhoto(null);
                    }
                    return bookDao.update(book);
                }

            } catch (DaoException | IOException e) {
                throw new ServiceException(e);
            }
        }
        return false;
    }

    @Override
    public boolean deleteBook(long bookId) throws ServiceException {
        BookDao bookDao = BookDaoImpl.getInstance();
        try {
            int actualQuantity = bookDao.getBooksQuantity(bookId);
            int quantity = bookDao.getBooksFreeQuantity(bookId);
            if (actualQuantity != quantity) {
                return false;
            } else {
                return bookDao.delete(bookId);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int countNumberOfBooks() throws ServiceException {
        BookDao bookDao = BookDaoImpl.getInstance();
        try {
            return bookDao.countNumberOfBooks();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Book> getAmount(int offSet, int amount) throws ServiceException {
        BookDao bookDao = BookDaoImpl.getInstance();
        try {
            return bookDao.getAll(offSet, amount);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private List<Book> getBooksByIds(List<Long> ids) throws ServiceException {
        List<Book> books = new ArrayList<>();
        BookDao bookDao = BookDaoImpl.getInstance();
        try {
            for (Long bookId : ids) {
                Optional<Book> optionalBook = bookDao.getBookById(bookId);
                if (optionalBook.isPresent()) {
                    books.add(optionalBook.get());
                } else {
                    throw new ServiceException("Invalid book id: " + bookId);
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return books;
    }

    private List<Author> getAuthorsByIds(List<Long> ids) throws ServiceException {
        List<Author> authors = new ArrayList<>();
        try {
            AuthorDao authorDao = AuthorDaoImpl.getInstance();
            for (Long id : ids) {
                Optional<Author> author = authorDao.getAuthorById(id);
                if (author.isEmpty()) {
                    throw new ServiceException("No author with such id: " + id);
                }
                authors.add(author.get());
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return authors;
    }

    private List<Genre> getGenresByIds(List<Long> ids) throws ServiceException {
        List<Genre> genres = new ArrayList<>();
        try {
            GenreDao genreDao = GenreDaoImpl.getInstance();
            for (Long id : ids) {
                Optional<Genre> genre = genreDao.getGenreById(id);
                if (genre.isEmpty()) {
                    throw new ServiceException("No such genre with id: " + id);
                }
                genres.add(genre.get());
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return genres;
    }

    private boolean isValidBook(Map<String, Object> mapBook, Set<String> invalids) {
        BookValidator validator = BookValidatorImpl.getInstance();
        if (!validator.isValidName((String) mapBook.get(ParameterName.BOOK_NAME))) {
            invalids.add(ParameterName.INVALID_BOOK_NAME);
        }
        if (!validator.isValidAbout((String) mapBook.get(ParameterName.BOOK_INFO))) {
            invalids.add(ParameterName.INVALID_BOOK_INFO);
        }
        if (!validator.isValidQuantity((String) mapBook.get(ParameterName.BOOK_QUANTITY))) {
            invalids.add(ParameterName.INVALID_BOOK_QUANTITY);
        }
        if (mapBook.containsKey(ParameterName.BOOK_COVER_PHOTO)) {
            if (!validator.isValidPicture((Part) mapBook.get(ParameterName.BOOK_COVER_PHOTO))) {
                invalids.add(ParameterName.INVALID_BOOK_COVER_PHOTO);
            }
        }
        if (!validator.isValidReleaseDate((String) mapBook.get(ParameterName.BOOK_RELEASE_DATE))) {
            invalids.add(ParameterName.INVALID_BOOK_RELEASE_DATE);
        }
        if (!validator.isValidAgeLimitations((String) mapBook.get(ParameterName.BOOK_AGE_LIMITATIONS))) {
            invalids.add(ParameterName.INVALID_BOOK_AGE_LIMITATIONS);
        }
        String[] authors = (String[]) mapBook.get(ParameterName.BOOK_AUTHORS);
        if (!validator.isValidIdsArray(authors)) {
            invalids.add(ParameterName.INVALID_BOOK_AUTHORS);
        }
        String[] genres = (String[]) mapBook.get(ParameterName.BOOK_GENRES);
        if (!validator.isValidIdsArray(genres)) {
            invalids.add(ParameterName.INVALID_BOOK_GENRES);
        }
        return invalids.isEmpty();
    }
}
