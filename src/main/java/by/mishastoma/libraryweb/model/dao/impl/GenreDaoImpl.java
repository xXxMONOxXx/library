package by.mishastoma.libraryweb.model.dao.impl;

import by.mishastoma.libraryweb.controller.pool.ConnectionPool;
import by.mishastoma.libraryweb.model.dao.GenreDao;
import by.mishastoma.libraryweb.model.dao.TableColumn;
import by.mishastoma.libraryweb.model.entity.Genre;
import by.mishastoma.libraryweb.exception.DaoException;
import by.mishastoma.libraryweb.model.mapper.CustomRowMapper;
import by.mishastoma.libraryweb.model.mapper.impl.GenreMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GenreDaoImpl implements GenreDao {

    private static final Logger logger = LogManager.getLogger();

    private static final String ADD_NEW_GENRE = """
            INSERT INTO genres (name)
            VALUES(?)""";

    private static final String SELECT_ALL_GENRES = """
            SELECT * FROM genres """;
    //todo add pagination

    private static final String SELECT_GENRE_BY_NAME = """
            SELECT id FROM genres WHERE name = ? """;

    private static final String SELECT_GENRE_BY_ID = """
            SElECT id, name FROM genres WHERE id = ? """;

    private static final String UPDATE_GENRE = """
            UPDATE genres SET name = ? WHERE id = ?""";

    private static GenreDaoImpl instance = new GenreDaoImpl();

    private GenreDaoImpl() {

    }

    public static GenreDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean insert(Genre genre) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_NEW_GENRE)) {
            statement.setString(1, genre.getName());
            if(statement.executeUpdate()==0){
                return false;
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return true;
    }

    @Override
    public boolean delete(Genre genre) throws DaoException {
        throw new UnsupportedOperationException(); //todo
    }

    @Override
    public List<Genre> findAll() throws DaoException {
        List<Genre> genres = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_GENRES)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CustomRowMapper<Genre> mapper = GenreMapper.getInstance();
                Optional<Genre> genre = mapper.map(resultSet);
                genre.ifPresent(genres::add);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return genres;
    }

    @Override
    public boolean update(Genre genre) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_GENRE)) {
            statement.setString(1, genre.getName());
            statement.setLong(2, genre.getId());
            if(statement.executeUpdate() == 1){
                return true;
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return false;
    }

    @Override
    public long getIdByName(String name) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_GENRE_BY_NAME)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? resultSet.getLong(TableColumn.ID) : -1;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Genre> getGenreById(long id) throws DaoException {
        Optional<Genre> optionalGenre = Optional.empty();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_GENRE_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                optionalGenre = Optional.of(new Genre(id, resultSet.getString(TableColumn.NAME)));
            }

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return optionalGenre;
    }
}
