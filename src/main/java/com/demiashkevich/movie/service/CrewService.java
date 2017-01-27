package com.demiashkevich.movie.service;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.dao.CrewDAO;
import com.demiashkevich.movie.entity.Crew;
import com.demiashkevich.movie.exception.DAOException;
import com.demiashkevich.movie.exception.ServiceException;

import java.util.List;

public class CrewService extends AbstractService {

    public CrewService() {}

    public List<Crew> findAllCrews() throws ServiceException {
        try {
            connection = ConnectionPool.takeConnection();

            return new CrewDAO(connection).findAllItems();
        } catch (DAOException exception) {
            throw new ServiceException(exception);
        } finally {
            ConnectionPool.putConnection(connection);
        }
    }

}
