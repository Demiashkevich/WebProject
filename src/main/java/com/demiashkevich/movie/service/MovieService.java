package com.demiashkevich.movie.service;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.dao.*;
import com.demiashkevich.movie.entity.*;
import com.demiashkevich.movie.validation.MovieValidationStrategy;
import com.demiashkevich.movie.validation.ValidationStrategy;

import java.util.List;

public class MovieService extends AbstractService {

    private ValidationStrategy<Movie> validationStrategy;

    public MovieService(ProxyConnection connection) {
        super(connection);
        validationStrategy = new MovieValidationStrategy();
    }

    public List<Movie> findAllMovies() {
        return new MovieDAO(connection).findAllItems();
    }

    public boolean addMovie(Movie movie) {
        Movie movieResult = this.parseMovieParameters(movie);
        MovieDAO movieDAO = new MovieDAO(connection);
//        if(validationStrategy.validate(movie)) {
            movieDAO.addItem(movieResult);
            return true;
//        }
//        return false;
    }

    public List<Movie> findMovies(int page, int count){
        int from = (page - 1)*count;
        MovieDAO movieDAO = new MovieDAO(connection);
        return movieDAO.findItems(from, count);
    }

    public int countPage(int countMovie){
        MovieDAO movieDAO = new MovieDAO(connection);
        int records = movieDAO.findCountRecords();
        return (int)Math.ceil((double)records / countMovie);
    }

    public Movie findMovie(Integer movieId) {
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

    public List<Movie> findMovies(int count) {
        MovieDAO movieDAO = new MovieDAO(connection);
        return movieDAO.findItemsSortByRating(count);
    }

    public boolean updateMovie(Movie movie){
        Movie movieResult = this.parseMovieParameters(movie);
        MovieDAO  movieDAO = new MovieDAO(connection);
        if(movieDAO.updateItem(movieResult)){
            return true;
        }
        return false;
    }

    public boolean deleteMovie(long itemId) {
        MovieDAO movieDAO = new MovieDAO(connection);
        movieDAO.deleteItem(itemId);
        return true;
    }

    private Movie parseMovieParameters(Movie movie){
        List<Country> countries = this.containListItems(movie.getCountries());
        List<Category> categories = this.containListItems(movie.getCategories());
        List<Actor> actors = this.containListItems(movie.getActors());
        List<Crew> crews = this.containListItems(movie.getCrews());
        movie.setCountries(countries);
        movie.setCategories(categories);
        movie.setActors(actors);
        movie.setCrews(crews);
        return movie;
    }

}
