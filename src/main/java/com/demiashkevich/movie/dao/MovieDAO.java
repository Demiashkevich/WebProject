package com.demiashkevich.movie.dao;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.*;

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
    private static final String INSERT_MOVIE = "INSERT INTO movie(title, date, description, length, poster) VALUES (?,?,?,?,?)";
    private static final String INSERT_CATEGORY_MOVIE = "INSERT INTO category_movie(category_id, movie_id) VALUES (?, LAST_GENERATE_ID())";
    private static final String INSERT_COUNTRY_MOVIE = "INSERT INTO movie_country(movie_id, country_id) VALUES (LAST_GENERATE_ID(),?)";
    private static final String INSERT_PERSON_ROLE_MOVIE = "INSERT INTO movie_person_role(movie_id, person_id, role_id) VALUES (LAST_GENERATE_ID(),?,?";
    private static final String INSERT_ACTOR_MOVIE = "INSERT INTO movie_actor(movie_id, actor_id) VALUES (LAST_GENERATE_ID(),?)";
    private static final String DELETE_MOVIE = "UPDATE movie SET movie.status = 0 WHERE movie.movie_id = ?";
    private static final String SELECT_MOVIE_BY_MOVIE_ID = "SELECT movie.movie_id, movie.title, movie.date, movie.description, movie.length, movie.poster, movie.rating FROM movie WHERE movie.movie_id = ?";
    private static final String SELECT_MOVIE_BY_ACTOR_ID = "SELECT movie.movie_id, movie.title, movie.poster FROM movie INNER JOIN movie_actor ON movie.movie_id = movie_actor.movie_id WHERE movie_actor.actor_id = ?";
    private static final String UPDATE_MOVIE = "UPDATE movie SET movie.title = ?, movie.date = ?, movie.description = ?, movie.length = ? WHERE movie.movie_id = ?";
    private static final String DELETE_LINKED_INF_BY_MOVIE_ID = "DELETE movie_country, category_movie, movie_actor, movie_person_role FROM movie_country INNER JOIN movie ON movie.movie_id = movie_country.movie_id INNER JOIN category_movie ON movie.movie_id = category_movie.movie_id INNER JOIN movie_actor ON movie.movie_id = movie_actor.movie_id INNER JOIN movie_person_role ON movie.movie_id = movie_person_role.movie_id WHERE movie.movie_id = ?";
    private static final String INSERT_COUNTRY_WITH_MOVIE_ID = "INSERT INTO movie_country(movie_id, country_id) VALUES (?,?)";
    private static final String INSERT_CATEGORY_WITH_MOVIE_ID = "INSERT INTO category_movie(movie_id, category_id) VALUES (?,?)";
    private static final String INSERT_CREW_WITH_MOVIE_ID = "INSERT INTO movie_person_role(movie_id, person_id, role_id) VALUES (?,?,?)";
    private static final String INSERT_ACTOR_WITH_MOVIE_ID = "INSERT INTO movie_actor(movie_id, actor_id) VALUES (?,?)";

    public MovieDAO(ProxyConnection connection) {
        super(connection);
    }

    @Override
    protected List<Movie> parseResultSetFull(ResultSet resultSet) {
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
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

    @Override
    public boolean addItem(Movie movie) {
        try {
            connection.setAutoCommit(false);
            try(PreparedStatement statementMovie = connection.prepareStatement(INSERT_MOVIE)) {
                statementMovie.setString(1, movie.getTitle());
                statementMovie.setDate(2, movie.getDate());
                statementMovie.setString(3, movie.getDescription());
                statementMovie.setInt(4, movie.getLength());
                statementMovie.setString(5, movie.getPoster());
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean updateItem(Movie movie){
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getSelectItemAll() {
        return SELECT_MOVIE_ALL;
    }

    @Override
    protected String getSelectItemLimitByRating() {
        return SELECT_MOVIE_LIMIT_BY_RATING;
    }

    @Override
    protected String getSelectItemByMovieId() {
        return SELECT_MOVIE_BY_MOVIE_ID;
    }

    @Override
    protected String getSelectItemByActorId() {
        return SELECT_MOVIE_BY_ACTOR_ID;
    }

    @Override
    protected String getDeleteItemById() {
        return DELETE_MOVIE;
    }

    @Override
    protected String getSelectItemLimit() {
        return SELECT_MOVIE_LIMIT;
    }

    @Override
    protected String getSelectNumberRowItem() {
        return SELECT_NUMBER_ROW_MOVIE;
    }

}
