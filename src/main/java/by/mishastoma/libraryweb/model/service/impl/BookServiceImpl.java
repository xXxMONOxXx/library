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
            String [] authorsIds = (String[]) bookMap.get(ParameterName.BOOK_AUTHORS);
            List<Long> authorsIdsLong = Stream.of(authorsIds).map(Long::valueOf).collect(Collectors.toList());
            List<Author> authors = getAuthorsByIds(authorsIdsLong);

            String [] genresIds = (String[]) bookMap.get(ParameterName.BOOK_GENRES);
            List<Long> genresIdsLong = Stream.of(genresIds).map(Long::valueOf).collect(Collectors.toList());
            List<Genre> genres = getGenresByIds(genresIdsLong);

            Part coverPhoto = (Part) bookMap.get(ParameterName.BOOK_COVER_PHOTO);
            try {
                Book book = new Book.Builder(-1).  //todo is it normal? I think not ;(
                        withName((String) bookMap.get(ParameterName.BOOK_NAME)).
                        withInfo((String) bookMap.get(ParameterName.BOOK_INFO)).
                        withReleaseDate(LocalDate.parse((String) bookMap.get(ParameterName.BOOK_RELEASE_DATE))).
                        withGenres(genres).
                        withAuthors(authors).
                        withAgeLimitations(Integer.valueOf((String) bookMap.get(ParameterName.BOOK_AGE_LIMITATIONS))).
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
        try{
            BookDao dao = BookDaoImpl.getInstance();
            optionalBook = dao.getBookById(id);
            if(optionalBook.isPresent()){
                long bookId = optionalBook.get().getId();
                int quantity = dao.getBooksQuantity(bookId);
                optionalBook.get().setQuantity(quantity);
                optionalBook.get().setAuthors(getAuthorsByIds(dao.getBooksAuthorsIds(bookId)));
                optionalBook.get().setGenres(getGenresByIds(dao.getBooksGenresIds(bookId)));
            }
        }
        catch (DaoException e) {
            throw new ServiceException(e);
        }
        return optionalBook;
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
        if (!validator.isValidPicture((Part) mapBook.get(ParameterName.BOOK_COVER_PHOTO))) {
            invalids.add(ParameterName.INVALID_BOOK_COVER_PHOTO);
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
