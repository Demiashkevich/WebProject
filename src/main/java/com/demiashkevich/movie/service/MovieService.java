package com.demiashkevich.movie.service;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.dao.*;
import com.demiashkevich.movie.entity.*;
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
    public List<Movie> findAllItem() {
        return new MovieDAO(connection).findAll();
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

    public List<Movie> findItems(int page, int count){
        int from = (page - 1)*count;
        MovieDAO movieDAO = new MovieDAO(connection);
        return movieDAO.find(from, count);
    }

    public int countPage(int countMovie){
        MovieDAO movieDAO = new MovieDAO(connection);
        int records = movieDAO.findCountRow();
        return (int)Math.ceil((double)records / countMovie);
    }

    @Override
    public Movie findItem(Integer movieId) {
        MovieDAO movieDAO = new MovieDAO(connection);
        Movie movie = movieDAO.findItemsByMovieId(movieId, FULL_OCCUPANCY).get(0);

        CountryDAO countryDAO = new CountryDAO(connection);
        List<Country> countries = countryDAO.findItemsByMovieId(movieId, LAZY_OCCUPANCY);
        movie.setCountries(countries);

        CategoryDAO categoryDAO = new CategoryDAO(connection);
        List<Category> categories = categoryDAO.findItemsByMovieId(movieId, LAZY_OCCUPANCY);
        movie.setCategories(categories);

        CrewDAO crewDAO = new CrewDAO(connection);
        List<Crew> crews = crewDAO.findItemsByMovieId(movieId, FULL_OCCUPANCY);
        movie.setCrews(crews);

        ActorDAO actorDAO = new ActorDAO(connection);
        List<Actor> actors = actorDAO.findItemsByMovieId(movieId, LAZY_OCCUPANCY);
        movie.setActors(actors);

        EvaluationDAO evaluationDAO = new EvaluationDAO(connection);
        List<Evaluation> evaluations = evaluationDAO.findItemsByMovieId(movieId, FULL_OCCUPANCY);
        movie.setEvaluations(evaluations);

        return movie;
    }

    @Override
    public List<Movie> findItems(final int COUNT) {
        MovieDAO movieDAO = new MovieDAO(connection);
        return movieDAO.findSortByRating(COUNT);
    }

    @Override
    public boolean deleteItem(long itemId) {
        MovieDAO movieDAO = new MovieDAO(connection);
        movieDAO.deleteItem(itemId);
        return true;
    }

}
