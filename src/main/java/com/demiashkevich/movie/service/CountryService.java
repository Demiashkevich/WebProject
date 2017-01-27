package com.demiashkevich.movie.service;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.dao.CountryDAO;
import com.demiashkevich.movie.entity.Country;
import com.demiashkevich.movie.exception.DAOException;
import com.demiashkevich.movie.exception.ServiceException;

import java.util.List;

public class CountryService extends AbstractService {

    public CountryService() {}

    public List<Country> findAllCountries() throws ServiceException {
        try {
            connection = ConnectionPool.takeConnection();

            return new CountryDAO(connection).findAllItems();
        } catch (DAOException exception) {
            throw new ServiceException(exception);
        } finally {
            ConnectionPool.putConnection(connection);
        }
    }

}
