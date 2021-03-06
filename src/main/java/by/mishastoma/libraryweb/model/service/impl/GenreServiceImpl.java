package by.mishastoma.libraryweb.model.service.impl;

import by.mishastoma.libraryweb.exception.DaoException;
import by.mishastoma.libraryweb.exception.ServiceException;
import by.mishastoma.libraryweb.model.dao.BaseDao;
import by.mishastoma.libraryweb.model.dao.BookDao;
import by.mishastoma.libraryweb.model.dao.GenreDao;
import by.mishastoma.libraryweb.model.dao.impl.BookDaoImpl;
import by.mishastoma.libraryweb.model.dao.impl.GenreDaoImpl;
import by.mishastoma.libraryweb.model.entity.Genre;
import by.mishastoma.libraryweb.model.service.GenreService;
import by.mishastoma.libraryweb.validator.GenreValidator;
import by.mishastoma.libraryweb.validator.impl.GenreValidatorImpl;

import java.util.List;
import java.util.Optional;

public class GenreServiceImpl implements GenreService {

    private static GenreServiceImpl instance = new GenreServiceImpl();

    private GenreServiceImpl() {

    }

    public static GenreServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Genre> getAll() throws ServiceException {
        List<Genre> genres;
        try {
            BaseDao<Genre> genreDao = GenreDaoImpl.getInstance();
            genres = genreDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return genres;
    }

    @Override
    public Optional<Genre> addGenre(String name) throws ServiceException {
        Optional<Genre> optionalGenre = Optional.empty();
        GenreValidator validator = GenreValidatorImpl.getInstance();
        if (validator.isValidName(name)) {
            try {
                Genre genre = new Genre(-1, name);
                GenreDao genreDao = GenreDaoImpl.getInstance();
                if (genreDao.getIdByName(name) == -1) {
                    genreDao.insert(genre);
                    genre.setId(genreDao.getIdByName(genre.getName()));
                    optionalGenre = Optional.of(genre);
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return optionalGenre;
    }

    @Override
    public Optional<Genre> getById(long id) throws ServiceException {
        Optional<Genre> optionalGenre = Optional.empty();
        GenreDao genreDao = GenreDaoImpl.getInstance();
        try {
            optionalGenre = genreDao.getGenreById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return optionalGenre;
    }

    @Override
    public boolean updateGenre(long id, String name) throws ServiceException {
        GenreValidator validator = GenreValidatorImpl.getInstance();
        if (!validator.isValidName(name)) {
            return false;
        }
        GenreDao genreDao = GenreDaoImpl.getInstance();
        try {
            return genreDao.update(new Genre(id, name));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteGenre(long id) throws ServiceException {
        GenreDao genreDao = GenreDaoImpl.getInstance();
        BookDao bookDao = BookDaoImpl.getInstance();
        try {
            if (!bookDao.deleteGenreFromBooks(id)) {
                return false;
            }
            return genreDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
