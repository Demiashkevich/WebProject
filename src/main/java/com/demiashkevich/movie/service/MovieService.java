package com.demiashkevich.movie.service;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.dao.MovieDAO;
import com.demiashkevich.movie.entity.Movie;
import com.demiashkevich.movie.validation.MovieValidationStrategy;
import com.demiashkevich.movie.validation.ValidationStrategy;

import java.util.List;

public class MovieService extends AbstractService<Movie, Integer> {

    private ValidationStrategy<Movie> validationStrategy;

    public MovieService(ProxyConnection connection) {
        super(connection);
        validationStrategy = new MovieValidationStrategy();
    }

    @Override
    public boolean addItem(Movie movie) {
        MovieDAO movieDAO = new MovieDAO(connection);
        if(validationStrategy.validate(movie)) {
            movieDAO.addItem(movie);
            return true;
        }
        return false;
    }

    @Override
    public Movie getItem(Integer movieId) {
        return null;
    }

    @Override
    public List<Movie> getItems(final int COUNT) {
        MovieDAO movieDAO = new MovieDAO(connection);
        return movieDAO.findSortByRating(COUNT);
    }

}
