package com.demiashkevich.movie.service;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.dao.CrewDAO;
import com.demiashkevich.movie.entity.Crew;

import java.util.List;

public class CrewService extends AbstractService {

    public CrewService(ProxyConnection connection) {
        super(connection);
    }

    public List<Crew> findAllCrews() {
        return new CrewDAO(connection).findAllItems();
    }

}
