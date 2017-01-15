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

    @Override
    protected String getSelectItemAll() {
        return SELECT_ALL_COUNTY;
    }

    @Override
    protected String getSelectItemLimitByRating() {
        return null;
    }

    @Override
    protected String getSelectItemByMovieId() {
        return SELECT_COUNTRY_BY_MOVIE_ID;
    }

    @Override
    protected String getSelectItemByActorId() {
        return null;
    }

    @Override
    protected String getDeleteItemById() {
        return null;
    }

    @Override
    protected String getSelectItemLimit() {
        return null;
    }

    @Override
    protected String getSelectNumberRowItem() {
        return null;
    }

}
