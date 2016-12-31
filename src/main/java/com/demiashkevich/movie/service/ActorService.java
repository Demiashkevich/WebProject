package com.demiashkevich.movie.service;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.dao.ActorDAO;
import com.demiashkevich.movie.entity.Actor;
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
    public boolean addItem(Actor actor) {
        ActorDAO actorDAO = new ActorDAO(this.connection);
        if(validationStrategy.validate(actor)) {
        actorDAO.addItem(actor);
            return true;
        }
        return false;
    }

    @Override
    public Actor getItem(Integer actorId) {
        return null;
    }

    @Override
    public List<Actor> getItems(final int COUNT) {
        ActorDAO actorDAO = new ActorDAO(connection);
        return actorDAO.findSortByRating(COUNT);
    }

}
