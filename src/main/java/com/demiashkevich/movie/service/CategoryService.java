package com.demiashkevich.movie.service;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.dao.CategoryDAO;
import com.demiashkevich.movie.entity.Category;
import com.demiashkevich.movie.exception.DAOException;
import com.demiashkevich.movie.exception.ServiceException;

import java.util.List;

public class CategoryService extends AbstractService {

    public CategoryService() {}

    public List<Category> findAllCategories() throws ServiceException {
        try {
            connection = ConnectionPool.takeConnection();

            return new CategoryDAO(connection).findAllItems();
        } catch (DAOException exception) {
            throw new ServiceException(exception);
        } finally {
            ConnectionPool.putConnection(connection);
        }
    }

}
