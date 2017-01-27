package com.demiashkevich.movie.service;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.dao.RoleDAO;
import com.demiashkevich.movie.entity.Role;
import com.demiashkevich.movie.exception.DAOException;
import com.demiashkevich.movie.exception.ServiceException;

import java.util.List;

public class RoleService extends AbstractService {

    public RoleService() {}

    public List<Role> findAllRoles() throws ServiceException {
        try {
            connection = ConnectionPool.takeConnection();

            return new RoleDAO(connection).findAllItems();
        } catch (DAOException exception) {
            throw new ServiceException(exception);
        } finally {
            ConnectionPool.putConnection(connection);
        }
    }

}
