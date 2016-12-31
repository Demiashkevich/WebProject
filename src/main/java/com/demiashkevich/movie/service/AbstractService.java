package com.demiashkevich.movie.service;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.Entity;

public abstract class AbstractService<T extends Entity, K> implements Service<T, K>{

    protected ProxyConnection connection;

    public AbstractService(ProxyConnection connection) {
        this.connection = connection;
    }

}
