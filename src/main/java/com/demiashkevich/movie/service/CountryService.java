package com.demiashkevich.movie.service;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.dao.CountryDAO;
import com.demiashkevich.movie.entity.Country;

import java.util.List;

public class CountryService extends AbstractService {

    public CountryService(ProxyConnection connection) {
        super(connection);
    }

    public List<Country> findAllCountries() {
        return new CountryDAO(connection).findAllItems();
    }

}
