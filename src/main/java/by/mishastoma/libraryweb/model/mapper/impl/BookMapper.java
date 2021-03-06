package by.mishastoma.libraryweb.model.mapper.impl;

import by.mishastoma.libraryweb.exception.DaoException;
import by.mishastoma.libraryweb.model.dao.TableColumn;
import by.mishastoma.libraryweb.model.entity.Book;
import by.mishastoma.libraryweb.model.mapper.CustomRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class BookMapper implements CustomRowMapper<Book> {

    private static BookMapper instance;

    public static BookMapper getInstance() {
        if (instance == null) {
            instance = new BookMapper();
        }
        return instance;
    }

    private BookMapper() {

    }

    @Override
    public Optional<Book> map(ResultSet resultSet) throws DaoException {
        Book book;
        Optional<Book> optionalBook;
        try {
            LocalDate releaseDate = LocalDate.parse(resultSet.getString(TableColumn.RELEASE_DATE));
            book = new Book.Builder(resultSet.getLong(TableColumn.ID)).
                    withName(resultSet.getString(TableColumn.NAME)).
                    withInfo(resultSet.getString(TableColumn.INFO)).
                    withReleaseDate(releaseDate).
                    withAgeLimitations(resultSet.getInt(TableColumn.AGE_LIMITATIONS)).
                    withCoverPhoto(resultSet.getBinaryStream(TableColumn.COVER_PHOTO)).
                    build();
            optionalBook = Optional.of(book);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return optionalBook;
    }
}
