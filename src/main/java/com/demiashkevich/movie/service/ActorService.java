package com.demiashkevich.movie.service;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.dao.ActorDAO;
import com.demiashkevich.movie.dao.MovieDAO;
import com.demiashkevich.movie.entity.Actor;
import com.demiashkevich.movie.entity.Movie;
import com.demiashkevich.movie.exception.DAOException;
import com.demiashkevich.movie.exception.ServiceException;
import com.demiashkevich.movie.validation.ActorValidation;
import com.demiashkevich.movie.validation.Validation;

import java.util.List;

public class ActorService extends AbstractService {

    public ActorService() {}

    public List<Actor> findAllActors() throws ServiceException {
        try {
            connection = ConnectionPool.takeConnection();

            return new ActorDAO(connection).findAllItems();
        } catch (DAOException exception) {
            throw new ServiceException(exception);
        } finally {
            ConnectionPool.putConnection(connection);
        }
    }

    public boolean addActor(Actor actor) throws ServiceException {
        try {
            connection = ConnectionPool.takeConnection();

            Actor actorResult = this.parseMovieParameters(actor);
            ActorDAO actorDAO = new ActorDAO(this.connection);
            Validation<Actor> validation = new ActorValidation();
            if(!validation.execute(actor)){
                return false;
            }
            actorDAO.addItem(actorResult);
        } catch (DAOException exception) {
            throw new ServiceException(exception);
        } finally {
            ConnectionPool.putConnection(connection);
        }
        return true;
    }

    public List<Actor> findActors(int page, int count) throws ServiceException {
        try {
            connection = ConnectionPool.takeConnection();

            int from = (page - 1)*count;
            ActorDAO actorDAO = new ActorDAO(connection);
            return actorDAO.findItems(from, count);
        } catch (DAOException exception) {
            throw new ServiceException(exception);
        } finally {
            ConnectionPool.putConnection(connection);
        }
    }

    public int countPage(int countActor) throws ServiceException {
        try {
            connection = ConnectionPool.takeConnection();

            ActorDAO actorDAO = new ActorDAO(connection);
            int records = actorDAO.findCountRecords();
            return (int)Math.ceil((double)records / countActor);
        } catch (DAOException exception) {
            throw new ServiceException(exception);
        } finally {
            ConnectionPool.putConnection(connection);
        }
    }

    public Actor findActor(Integer actorId) throws ServiceException {
        Actor actor = null;
        try {
            connection = ConnectionPool.takeConnection();

            ActorDAO actorDAO = new ActorDAO(connection);
            List<Actor> actors =  actorDAO.findItemsByActorId(actorId, FULL_OCCUPANCY);
            if(!actors.isEmpty()){
                actor = actors.get(0);

                MovieDAO movieDAO = new MovieDAO(connection);
                List<Movie> movies = movieDAO.findItemsByActorId(actorId, LAZY_OCCUPANCY);
                actor.setMovies(movies);
            }
        } catch (DAOException exception) {
            throw new ServiceException(exception);
        } finally {
            ConnectionPool.putConnection(connection);
        }
        return actor;
    }

    public List<Actor> findActors(int count) throws ServiceException {
        try {
            connection = ConnectionPool.takeConnection();

            ActorDAO actorDAO = new ActorDAO(connection);
            return actorDAO.findItems(0, count);
        } catch (DAOException exception) {
            throw new ServiceException(exception);
        }  finally {
            ConnectionPool.putConnection(connection);
        }
    }

    public boolean deleteActor(long itemId) throws ServiceException {
        try {
            connection = ConnectionPool.takeConnection();

            ActorDAO actorDAO = new ActorDAO(connection);
            actorDAO.deleteItem(itemId);
            return true;
        } catch (DAOException exception) {
            throw new ServiceException(exception);
        } finally {
            ConnectionPool.putConnection(connection);
        }
    }

    private Actor parseMovieParameters(Actor actor){
        List<Movie> movies = this.containListItems(actor.getMovies());
        actor.setMovies(movies);
        return actor;
    }

}
