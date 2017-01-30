package com.demiashkevich.movie.dao;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.*;
import com.demiashkevich.movie.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO extends AbstractDAO<Movie> {

    private static final String SELECT_MOVIE_ALL = "SELECT movie.movie_id, movie.title, movie.poster FROM movie";
    private static final String SELECT_MOVIE_LIMIT = "SELECT movie.movie_id, movie.title, movie.poster FROM movie LIMIT ?,?";
    private static final String SELECT_NUMBER_ROW_MOVIE = "SELECT COUNT(movie.movie_id) FROM movie";
    private static final String SELECT_MOVIE_LIMIT_BY_RATING = "SELECT movie.movie_id, movie.title, movie.poster FROM movie ORDER BY movie.rating DESC LIMIT ?";
    private static final String INSERT_MOVIE = "INSERT INTO movie(title, date, description, length, poster, status) VALUES (?,?,?,?,?,?)";
    private static final String INSERT_CATEGORY_MOVIE = "INSERT INTO category_movie(category_id, movie_id) VALUES (?, LAST_INSERT_ID())";
    private static final String INSERT_COUNTRY_MOVIE = "INSERT INTO movie_country(movie_id, country_id) VALUES (LAST_INSERT_ID(),?)";
    private static final String INSERT_PERSON_ROLE_MOVIE = "INSERT INTO movie_person_role(movie_id, person_id, role_id) VALUES (LAST_INSERT_ID(),?,?)";
    private static final String INSERT_ACTOR_MOVIE = "INSERT INTO movie_actor(movie_id, actor_id) VALUES (LAST_INSERT_ID(),?)";
    private static final String DELETE_MOVIE = "UPDATE movie SET movie.status = 0 WHERE movie.movie_id = ?";
    private static final String SELECT_MOVIE_BY_MOVIE_ID = "SELECT movie.movie_id, movie.title, movie.date, movie.description, movie.length, movie.poster, movie.rating, movie.status FROM movie WHERE movie.movie_id = ?";
    private static final String SELECT_MOVIE_BY_ACTOR_ID = "SELECT movie.movie_id, movie.title, movie.poster FROM movie INNER JOIN movie_actor ON movie.movie_id = movie_actor.movie_id WHERE movie_actor.actor_id = ?";
    private static final String UPDATE_MOVIE = "UPDATE movie SET movie.title = ?, movie.date = ?, movie.description = ?, movie.length = ? WHERE movie.movie_id = ?";
    private static final String DELETE_LINKED_INF_BY_MOVIE_ID = "DELETE movie_country, category_movie, movie_actor, movie_person_role FROM movie_country INNER JOIN movie ON movie.movie_id = movie_country.movie_id INNER JOIN category_movie ON movie.movie_id = category_movie.movie_id INNER JOIN movie_actor ON movie.movie_id = movie_actor.movie_id INNER JOIN movie_person_role ON movie.movie_id = movie_person_role.movie_id WHERE movie.movie_id = ?";
    private static final String INSERT_COUNTRY_WITH_MOVIE_ID = "INSERT INTO movie_country(movie_id, country_id) VALUES (?,?)";
    private static final String INSERT_CATEGORY_WITH_MOVIE_ID = "INSERT INTO category_movie(movie_id, category_id) VALUES (?,?)";
    private static final String INSERT_CREW_WITH_MOVIE_ID = "INSERT INTO movie_person_role(movie_id, person_id, role_id) VALUES (?,?,?)";
    private static final String INSERT_ACTOR_WITH_MOVIE_ID = "INSERT INTO movie_actor(movie_id, actor_id) VALUES (?,?)";
    private static final String SELECT_RATING_BY_MOVIE_ID = "SELECT movie.rating FROM movie WHERE movie.movie_id = ?";

    public MovieDAO(ProxyConnection connection) {
        super(connection);
    }

    @Override
    protected List<Movie> parseResultSetFull(ResultSet resultSet) throws DAOException {
        List<Movie> movies = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Movie movie = new Movie();
                movie.setMovieId(resultSet.getInt(1));
                movie.setTitle(resultSet.getString(2));
                movie.setDate(resultSet.getDate(3));
                movie.setDescription(resultSet.getString(4));
                movie.setLength(resultSet.getShort(5));
                movie.setPoster(resultSet.getString(6));
                movie.setRating(resultSet.getDouble(7));
                movie.setStatus(resultSet.getBoolean(8));
                movies.add(movie);
            }
        } catch (SQLException exception) {
            throw new DAOException(exception);
        }
        return movies;
    }

    @Override
    protected List<Movie> parseResultSetLazy(ResultSet resultSet) {
        List<Movie> movies = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Movie movie = new Movie();
                movie.setMovieId(resultSet.getInt(1));
                movie.setTitle(resultSet.getString(2));
                movie.setPoster(resultSet.getString(3));
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public double findRatingByMovieId(final long MOVIE_ID) throws DAOException {
        double rating;
        try(PreparedStatement statement = connection.prepareStatement(SELECT_RATING_BY_MOVIE_ID)){
            statement.setLong(1, MOVIE_ID);
            ResultSet resultSet = statement.executeQuery();
            rating = resultSet.getDouble(1);
        } catch (SQLException exception) {
            throw new DAOException(exception);
        }
        return rating;
    }

    @Override
    public boolean addItem(Movie movie) throws DAOException {
        try {
            connection.setAutoCommit(false);
            try(PreparedStatement statementMovie = connection.prepareStatement(INSERT_MOVIE)) {
                statementMovie.setString(1, movie.getTitle());
                statementMovie.setDate(2, movie.getDate());
                statementMovie.setString(3, movie.getDescription());
                statementMovie.setInt(4, movie.getLength());
                statementMovie.setString(5, movie.getPoster());
                statementMovie.setBoolean(6, true);
                statementMovie.executeUpdate();

                try(PreparedStatement statementCategory = connection.prepareStatement(INSERT_CATEGORY_MOVIE)) {
                    for(Category category : movie.getCategories()){
                        statementCategory.setInt(1, category.getCategoryId());
                        statementCategory.executeUpdate();
                    }
                }
                try(PreparedStatement statementCountry = connection.prepareStatement(INSERT_COUNTRY_MOVIE)) {
                    for(Country country : movie.getCountries()){
                        statementCountry.setInt(1, country.getCountryId());
                        statementCountry.executeUpdate();
                    }
                }
                try(PreparedStatement statementCrew = connection.prepareStatement(INSERT_PERSON_ROLE_MOVIE)) {
                    for(Crew crew : movie.getCrews()){
                        statementCrew.setInt(1, crew.getCrewId());
                        statementCrew.setInt(2, crew.getRole().getRoleId());
                        statementCrew.executeUpdate();
                    }
                }
                try(PreparedStatement statementActor = connection.prepareStatement(INSERT_ACTOR_MOVIE)) {
                    for(Actor actor : movie.getActors()){
                        statementActor.setInt(1, actor.getActorId());
                        statementActor.executeUpdate();
                    }
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException exception) {
            throw new DAOException(exception);
        }
    }

    @Override
    public List<Movie> findItemsByMovieId(final long MOVIE_ID, final boolean OCCUPANCY) throws DAOException {
        return this.find(SELECT_MOVIE_BY_MOVIE_ID, MOVIE_ID, OCCUPANCY);
    }

    @Override
    public List<Movie> findItemsByActorId(final long ACTOR_ID, final boolean OCCUPANCY) throws DAOException {
        return this.find(SELECT_MOVIE_BY_ACTOR_ID, ACTOR_ID, OCCUPANCY);
    }

    @Override
    public List<Movie> findAllItems() throws DAOException {
        return this.findAll(SELECT_MOVIE_ALL);
    }

    @Override
    public List<Movie> findItems(final int FROM, final int COUNT) throws DAOException {
        return this.find(SELECT_MOVIE_LIMIT, FROM, COUNT);
    }

    @Override
    public int findCountRecords() throws DAOException {
        return this.findCountRow(SELECT_NUMBER_ROW_MOVIE);
    }

    @Override
    public boolean deleteItem(final long MOVIE_ID) throws DAOException {
        return this.delete(DELETE_MOVIE, MOVIE_ID);
    }

    public List<Movie> findItemsSortByRating(final int COUNT) throws DAOException {
        return this.find(SELECT_MOVIE_LIMIT_BY_RATING, COUNT, false);
    }

    public boolean updateItem(Movie movie) throws DAOException {
        try {
            connection.setAutoCommit(false);
            try(PreparedStatement statement = connection.prepareStatement(UPDATE_MOVIE)) {
                statement.setString(1, movie.getTitle());
                statement.setDate(2, movie.getDate());
                statement.setString(3, movie.getDescription());
                statement.setInt(4, movie.getLength());
                statement.setLong(5, movie.getMovieId());
                statement.executeUpdate();
            }
            try(PreparedStatement statement = connection.prepareStatement(DELETE_LINKED_INF_BY_MOVIE_ID)){
                statement.setLong(1, movie.getMovieId());
                statement.executeUpdate();
            }
            try(PreparedStatement statement = connection.prepareStatement(INSERT_COUNTRY_WITH_MOVIE_ID)){
                for(Country country : movie.getCountries()) {
                    statement.setLong(1, movie.getMovieId());
                    statement.setInt(2, country.getCountryId());
                    statement.executeUpdate();
                }
            }
            try(PreparedStatement statement = connection.prepareStatement(INSERT_CATEGORY_WITH_MOVIE_ID)){
                for(Category category : movie.getCategories()){
                    statement.setLong(1, movie.getMovieId());
                    statement.setInt(2, category.getCategoryId());
                    statement.executeUpdate();
                }
            }
            try(PreparedStatement statement = connection.prepareStatement(INSERT_CREW_WITH_MOVIE_ID)){
                for(Crew crew : movie.getCrews()){
                    statement.setLong(1, movie.getMovieId());
                    statement.setInt(2, crew.getCrewId());
                    statement.setInt(3, crew.getRole().getRoleId());
                    statement.executeUpdate();
                }
            }
            try(PreparedStatement statement = connection.prepareStatement(INSERT_ACTOR_WITH_MOVIE_ID)){
                for(Actor actor : movie.getActors()){
                    statement.setLong(1, movie.getMovieId());
                    statement.setInt(2, actor.getActorId());
                    statement.executeUpdate();
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException exception) {
            throw new DAOException(exception);
        }
    }

}
