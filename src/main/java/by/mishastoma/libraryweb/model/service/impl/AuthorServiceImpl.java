package by.mishastoma.libraryweb.model.service.impl;

import by.mishastoma.libraryweb.controller.ParameterName;
import by.mishastoma.libraryweb.exception.DaoException;
import by.mishastoma.libraryweb.exception.ServiceException;
import by.mishastoma.libraryweb.model.dao.AuthorDao;
import by.mishastoma.libraryweb.model.dao.BaseDao;
import by.mishastoma.libraryweb.model.dao.BookDao;
import by.mishastoma.libraryweb.model.dao.impl.AuthorDaoImpl;
import by.mishastoma.libraryweb.model.dao.impl.BookDaoImpl;
import by.mishastoma.libraryweb.model.entity.Author;
import by.mishastoma.libraryweb.model.service.AuthorService;
import by.mishastoma.libraryweb.validator.AuthorValidator;
import by.mishastoma.libraryweb.validator.impl.AuthorValidatorImpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class AuthorServiceImpl implements AuthorService {

    private static AuthorServiceImpl instance = new AuthorServiceImpl();

    private AuthorServiceImpl() {

    }

    public static AuthorServiceImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<Author> addAuthor(Map<String, String> authorMap, Set<String> invalids) throws ServiceException {
        Optional<Author> optionalAuthor = Optional.empty();
        if (isValidAuthor(authorMap, invalids)) {
            Author author = new Author(-1,
                    authorMap.get(ParameterName.FIRST_NAME),
                    authorMap.get(ParameterName.LAST_NAME),
                    authorMap.get(ParameterName.BIOGRAPHY));
            AuthorDao dao = AuthorDaoImpl.getInstance();
            try {
                if (dao.insert(author)) {
                    author.setId(dao.getIdByAuthorsName(author.getFirstname(), author.getLastname()));
                    optionalAuthor = Optional.of(author);
                } else {
                    invalids.add(ParameterName.AUTHOR_ALREADY_EXISTS);
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return optionalAuthor;
    }

    @Override
    public List<Author> getAll() throws ServiceException {
        List<Author> authors;
        try {
            BaseDao<Author> authorDao = AuthorDaoImpl.getInstance();
            authors = authorDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return authors;
    }

    @Override
    public Optional<Author> getById(long id) throws ServiceException {
        Optional<Author> optionalAuthor = Optional.empty();
        AuthorDao dao = AuthorDaoImpl.getInstance();
        try {
            optionalAuthor = dao.getAuthorById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return optionalAuthor;
    }

    @Override
    public boolean updateAuthor(Map<String, String> authorMap, Set<String> invalids) throws ServiceException {
        if (!isValidAuthor(authorMap, invalids)) {
            return false;
        }

        Author author = new Author(Long.parseLong(authorMap.get(ParameterName.AUTHOR_ID)),
                authorMap.get(ParameterName.FIRST_NAME),
                authorMap.get(ParameterName.LAST_NAME),
                authorMap.get(ParameterName.BIOGRAPHY));
        AuthorDao dao = AuthorDaoImpl.getInstance();
        try {
            if (dao.update(author)) {
                return true;
            } else {
                invalids.add(ParameterName.AUTHOR_ALREADY_EXISTS);
                return false;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteAuthor(long id) throws ServiceException {
        AuthorDao authorDao = AuthorDaoImpl.getInstance();
        BookDao bookDao = BookDaoImpl.getInstance();
        try {
            if (bookDao.deleteAuthorFromBooks(id)) {
                return authorDao.delete(id);
            } else {
                return false;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private boolean isValidAuthor(Map<String, String> authorMap, Set<String> invalids) {
        AuthorValidator validator = AuthorValidatorImpl.getInstance();
        if (!validator.isValidFirstName(authorMap.get(ParameterName.FIRST_NAME))) {
            invalids.add(ParameterName.AUTHOR_FIRSTNAME_IS_INVALID);
        }
        if (!validator.isValidLastName(authorMap.get(ParameterName.LAST_NAME))) {
            invalids.add(ParameterName.AUTHOR_LASTNAME_IS_INVALID);
        }
        if (!validator.isValidBiography(authorMap.get(ParameterName.BIOGRAPHY))) {
            invalids.add(ParameterName.AUTHOR_BIOGRAPHY_IS_INVALID);
        }
        return invalids.isEmpty();
    }
}
