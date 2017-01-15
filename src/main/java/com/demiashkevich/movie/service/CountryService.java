package com.demiashkevich.movie.service;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.dao.CountryDAO;
import com.demiashkevich.movie.entity.Country;

import java.util.List;

public class CountryService extends AbstractService<Country, Integer> {

    public CountryService(ProxyConnection connection) {
        super(connection);
    }

    @Override
    public List<Country> findAllItem() {
        return new CountryDAO(connection).findAll();
    }

    @Override
    public boolean addItem(Country item) {
        return false;
    }

    @Override
    public Country findItem(Integer key) {
        return null;
    }

    @Override
    public List<Country> findItems(final int COUNT) {
        return null;
    }

    @Override
    public boolean deleteItem(long itemId) {
        return false;
    }
}
