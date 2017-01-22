package com.demiashkevich.movie.service;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.dao.CategoryDAO;
import com.demiashkevich.movie.entity.Category;

import java.util.List;

public class CategoryService extends AbstractService {

    public CategoryService(ProxyConnection connection) {
        super(connection);
    }

    public List<Category> findAllCategories() {
        return new CategoryDAO(connection).findAllItems();
    }

}
