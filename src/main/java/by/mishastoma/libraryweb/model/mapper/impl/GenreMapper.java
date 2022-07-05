package by.mishastoma.libraryweb.model.mapper.impl;

import by.mishastoma.libraryweb.exception.DaoException;
import by.mishastoma.libraryweb.model.dao.TableColumn;
import by.mishastoma.libraryweb.model.entity.Genre;
import by.mishastoma.libraryweb.model.mapper.CustomRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class GenreMapper implements CustomRowMapper<Genre> {

    private static GenreMapper instance;

    public static GenreMapper getInstance(){
        if(instance==null){
            instance = new GenreMapper();
        }
        return instance;
    }

    private GenreMapper(){

    }

    @Override
    public Optional<Genre> map(ResultSet resultSet) throws DaoException {
        Genre genre;
        Optional<Genre> optionalGenre;
        try{
            String name = resultSet.getString(TableColumn.NAME);
            long id = resultSet.getLong(TableColumn.ID);
            genre = new Genre(id, name);
            optionalGenre = Optional.of(genre);
        }
        catch (SQLException e){
            throw new DaoException(e);
        }
        return optionalGenre;
    }
}
