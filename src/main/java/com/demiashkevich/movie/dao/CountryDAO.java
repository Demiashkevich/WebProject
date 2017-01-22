package com.demiashkevich.movie.dao;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.Country;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryDAO extends AbstractDAO<Country> {

    private static final String SELECT_ALL_COUNTY = "SELECT country.country_id, country.name FROM country";
    private static final String SELECT_COUNTRY_BY_MOVIE_ID = "SELECT country.country_id, country.name FROM country INNER JOIN movie_country ON country.country_id = movie_country.country_id WHERE movie_country.movie_id = ?";

    public CountryDAO(ProxyConnection connection) {
        super(connection);
    }

    @Override
    public boolean addItem(Country item) {
        return false;
    }

    @Override
    public List<Country> findItemsByMovieId(final long MOVIE_ID, final boolean OCCUPANCY) {
        return this.find(SELECT_COUNTRY_BY_MOVIE_ID, MOVIE_ID, OCCUPANCY);
    }

    @Override
    public List<Country> findItemsByActorId(final long ACTOR_ID, final boolean OCCUPANCY) {
        return null;
    }

    @Override
    public List<Country> findAllItems() {
        return this.findAll(SELECT_ALL_COUNTY);
    }

    @Override
    public List<Country> findItems(final int FROM, final int COUNT) {
        return null;
    }

    @Override
    public int findCountRecords() {
        return 0;
    }

    @Override
    public boolean deleteItem(final long COUNTRY_ID) {
        return false;
    }

    @Override
    protected List<Country> parseResultSetFull(ResultSet resultSet) {
        return null;
    }

    @Override
    protected List<Country> parseResultSetLazy(ResultSet resultSet) {
        List<Country> countries = new ArrayList<>();
        try {
            while(resultSet.next()) {
                Country country = new Country();
                country.setCountryId(resultSet.getInt(1));
                country.setName(resultSet.getString(2));
                countries.add(country);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return countries;
    }

}
