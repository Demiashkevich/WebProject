package com.demiashkevich.movie.service;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.dao.ActorDAO;
import com.demiashkevich.movie.dao.MovieDAO;
import com.demiashkevich.movie.entity.Actor;
import com.demiashkevich.movie.entity.Movie;
import com.demiashkevich.movie.validation.ActorValidationStrategy;
import com.demiashkevich.movie.validation.ValidationStrategy;

import java.util.List;

public class ActorService extends AbstractService<Actor, Integer> {

    private ValidationStrategy<Actor> validationStrategy;

    public ActorService(ProxyConnection connection) {
        super(connection);
        validationStrategy = new ActorValidationStrategy();
    }

    @Override
    public List<Actor> findAllItem() {
        return new ActorDAO(connection).findAll();
    }

    @Override
    public boolean addItem(Actor actor) {
        ActorDAO actorDAO = new ActorDAO(this.connection);
//        if(validationStrategy.validate(actor)) {
            actorDAO.addItem(actor);
            return true;
//        }
//        return false;
    }

    public List<Actor> findItems(int page, int count){
        int from = (page - 1)*count;
        ActorDAO actorDAO = new ActorDAO(connection);
        return actorDAO.find(from, count);
    }

    public int countPage(int countActor){
        ActorDAO actorDAO = new ActorDAO(connection);
        int records = actorDAO.findCountRow();
        return (int)Math.ceil((double)records / countActor);
    }

    @Override
    public Actor findItem(Integer actorId) {
        ActorDAO actorDAO = new ActorDAO(connection);
        Actor actor = actorDAO.findItemsByActorId(actorId, FULL_OCCUPANCY).get(0);

        MovieDAO movieDAO = new MovieDAO(connection);
        List<Movie> movies = movieDAO.findItemsByActorId(actorId, LAZY_OCCUPANCY);
        actor.setMovies(movies);

        return actor;
    }

    @Override
    public List<Actor> findItems(final int COUNT) {
        ActorDAO actorDAO = new ActorDAO(connection);
        return actorDAO.findSortByRating(COUNT);
    }

    @Override
    public boolean deleteItem(long itemId) {
        ActorDAO actorDAO = new ActorDAO(connection);
        actorDAO.deleteItem(itemId);
        return true;
    }

}
