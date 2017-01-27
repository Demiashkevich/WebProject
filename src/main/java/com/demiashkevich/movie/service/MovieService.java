package com.demiashkevich.movie.service;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.dao.*;
import com.demiashkevich.movie.entity.*;
import com.demiashkevich.movie.exception.DAOException;
import com.demiashkevich.movie.exception.ServiceException;
import com.demiashkevich.movie.validation.MovieValidation;
import com.demiashkevich.movie.validation.Validation;

import java.util.List;

public class MovieService extends AbstractService {

    public MovieService() {}

    public List<Movie> findAllMovies() throws ServiceException {
        try {
            connection = ConnectionPool.takeConnection();

            return new MovieDAO(connection).findAllItems();
        } catch (DAOException exception) {
            throw new ServiceException(exception);
        } finally {
            ConnectionPool.putConnection(connection);
        }
    }

    public boolean addMovie(Movie movie) throws ServiceException {
        try {
            connection = ConnectionPool.takeConnection();

            Movie movieResult = this.parseMovieParameters(movie);
            MovieDAO movieDAO = new MovieDAO(connection);
            Validation<Movie> validation = new MovieValidation();
            if(!validation.execute(movie)){
                return false;
            }
            movieDAO.addItem(movieResult);
        } catch (DAOException exception) {
            throw new ServiceException(exception);
        } finally {
            ConnectionPool.putConnection(connection);
        }
        return true;
    }

    public List<Movie> findMovies(int page, int count) throws ServiceException {
        try {
            connection = ConnectionPool.takeConnection();

            int from = (page - 1)*count;
            MovieDAO movieDAO = new MovieDAO(connection);
            return movieDAO.findItems(from, count);
        } catch (DAOException exception) {
            throw new ServiceException(exception);
        } finally {
            ConnectionPool.putConnection(connection);
        }
    }

    public int countPage(int countMovie) throws ServiceException {
        try {
            connection = ConnectionPool.takeConnection();

            MovieDAO movieDAO = new MovieDAO(connection);
            int records = movieDAO.findCountRecords();
            return (int)Math.ceil((double)records / countMovie);
        } catch (DAOException exception) {
            throw new ServiceException(exception);
        } finally {
            ConnectionPool.putConnection(connection);
        }
    }

    public Movie findMovie(Integer movieId) throws ServiceException {
        Movie movie = null;
        try {
            connection = ConnectionPool.takeConnection();

            MovieDAO movieDAO = new MovieDAO(connection);
            List<Movie> movies = movieDAO.findItemsByMovieId(movieId, FULL_OCCUPANCY);
            if(!movies.isEmpty()) {
                movie = movies.get(0);

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
            }
        } catch (DAOException exception) {
            throw new ServiceException(exception);
        } finally {
            ConnectionPool.putConnection(connection);
        }
        return movie;
    }

    public List<Movie> findMovies(int count) throws ServiceException {
        try {
            connection = ConnectionPool.takeConnection();

            MovieDAO movieDAO = new MovieDAO(connection);
            return movieDAO.findItemsSortByRating(count);
        } catch (DAOException exception) {
            throw new ServiceException(exception);
        } finally {
            ConnectionPool.putConnection(connection);
        }
    }

    public boolean updateMovie(Movie movie) throws ServiceException {
        try {
            connection = ConnectionPool.takeConnection();
            Validation<Movie> validation = new MovieValidation();
            if(!validation.execute(movie)){
                return false;
            }
            Movie movieResult = this.parseMovieParameters(movie);
            MovieDAO  movieDAO = new MovieDAO(connection);
            movieDAO.updateItem(movieResult);
        } catch (DAOException exception) {
            throw new ServiceException(exception);
        } finally {
            ConnectionPool.putConnection(connection);
        }
        return true;
    }

    public boolean deleteMovie(long itemId) throws ServiceException {
        try {
            connection = ConnectionPool.takeConnection();

            MovieDAO movieDAO = new MovieDAO(connection);
            movieDAO.deleteItem(itemId);
        } catch (DAOException exception) {
            throw new ServiceException(exception);
        } finally {
            ConnectionPool.putConnection(connection);
        }
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
