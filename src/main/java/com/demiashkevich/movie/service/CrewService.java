package com.demiashkevich.movie.service;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.dao.CrewDAO;
import com.demiashkevich.movie.entity.Crew;

import java.util.List;

public class CrewService extends AbstractService<Crew, Integer> {

    public CrewService(ProxyConnection connection) {
        super(connection);
    }

    @Override
    public List<Crew> findAllItem() {
        return new CrewDAO(connection).findAll();
    }

    @Override
    public boolean addItem(Crew item) {
        return false;
    }

    @Override
    public Crew findItem(Integer key) {
        return null;
    }

    @Override
    public List<Crew> findItems(final int COUNT) {
        return null;
    }

    @Override
    public boolean deleteItem(long itemId) {
        return false;
    }
}
