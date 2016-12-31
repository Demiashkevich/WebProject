package com.demiashkevich.movie.dao;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.Movie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO extends AbstractDAO<Movie> {

    private static final String SELECT_ALL = "SELECT movie.title, movie.poster FROM movie";
    private static final String SELECT_LIMIT_BY_RATING = "SELECT movie.movie_id, movie.title, movie.date, movie.description, movie.length, movie.poster, movie.rating FROM movie ORDER BY movie.rating DESC LIMIT ?";
    private static final String INSERT_MOVIE = "INSERT INTO movie(title, date, description, length, poster) VALUES (?,?,?,?,?)";

    public MovieDAO(ProxyConnection connection) {
        super(connection);
    }

    @Override
    protected List<Movie> parseResultSet(ResultSet resultSet) {
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
    public boolean addItem(Movie movie) {
        try(PreparedStatement statement = connection.prepareStatement(INSERT_MOVIE)) {
            statement.setString(1, movie.getTitle());
            statement.setDate(2, movie.getDate());
            statement.setString(3, movie.getDescription());
            statement.setInt(4, movie.getLength());
            statement.setString(5, movie.getPoster());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public String getSelectAll() {
        return SELECT_ALL;
    }

    @Override
    protected String getSelectLimitByRating() {
        return SELECT_LIMIT_BY_RATING;
    }
}
