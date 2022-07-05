package by.mishastoma.libraryweb.model.mapper.impl;

import by.mishastoma.libraryweb.exception.DaoException;
import by.mishastoma.libraryweb.model.dao.TableColumn;
import by.mishastoma.libraryweb.model.entity.Author;
import by.mishastoma.libraryweb.model.mapper.CustomRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AuthorMapper implements CustomRowMapper<Author> {

    private static AuthorMapper instance;

    public static AuthorMapper getInstance(){
        if(instance==null){
            instance = new AuthorMapper();
        }
        return instance;
    }

    private AuthorMapper(){

    }

    @Override
    public Optional<Author> map(ResultSet resultSet) throws DaoException {
        Author author;
        Optional<Author> optionalAuthor;
        try{
            String firstname = resultSet.getString(TableColumn.FIRST_NAME);
            String lastname = resultSet.getString(TableColumn.LAST_NAME);
            String biography = resultSet.getString(TableColumn.BIO);
            long id = resultSet.getLong(TableColumn.ID);
            author = new Author(id, firstname,lastname ,biography);
            optionalAuthor = Optional.of(author);
        }
        catch (SQLException e){
            throw new DaoException(e);
        }
        return optionalAuthor;
    }
}
