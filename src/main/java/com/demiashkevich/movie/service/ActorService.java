package com.demiashkevich.movie.service;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.dao.ActorDAO;
import com.demiashkevich.movie.dao.MovieDAO;
import com.demiashkevich.movie.entity.*;
import com.demiashkevich.movie.validation.ActorValidationStrategy;
import com.demiashkevich.movie.validation.ValidationStrategy;

import java.util.List;

public class ActorService extends AbstractService {

    private ValidationStrategy<Actor> validationStrategy;

    public ActorService(ProxyConnection connection) {
        super(connection);
        validationStrategy = new ActorValidationStrategy();
    }

    public List<Actor> findAllActors() {
        return new ActorDAO(connection).findAllItems();
    }

    public boolean addActor(Actor actor) {
        Actor actorResult = this.parseMovieParameters(actor);
        ActorDAO actorDAO = new ActorDAO(this.connection);
//        if(validationStrategy.validate(actor)) {
            actorDAO.addItem(actorResult);
            return true;
//        }
//        return false;
    }

    public List<Actor> findActors(int page, int count){
        int from = (page - 1)*count;
        ActorDAO actorDAO = new ActorDAO(connection);
        return actorDAO.findItems(from, count);
    }

    public int countPage(int countActor){
        ActorDAO actorDAO = new ActorDAO(connection);
        int records = actorDAO.findCountRecords();
        return (int)Math.ceil((double)records / countActor);
    }

    public Actor findActor(Integer actorId) {
        ActorDAO actorDAO = new ActorDAO(connection);
        Actor actor = actorDAO.findItemsByActorId(actorId, FULL_OCCUPANCY).get(0);

        MovieDAO movieDAO = new MovieDAO(connection);
        List<Movie> movies = movieDAO.findItemsByActorId(actorId, LAZY_OCCUPANCY);
        actor.setMovies(movies);

        return actor;
    }

    public List<Actor> findActors(int count) {
        ActorDAO actorDAO = new ActorDAO(connection);
        return actorDAO.findItems(0, count);
    }

    public boolean deleteActor(long itemId) {
        ActorDAO actorDAO = new ActorDAO(connection);
        actorDAO.deleteItem(itemId);
        return true;
    }

    private Actor parseMovieParameters(Actor actor){
        List<Movie> movies = this.containListItems(actor.getMovies());
        actor.setMovies(movies);
        return actor;
    }

}
