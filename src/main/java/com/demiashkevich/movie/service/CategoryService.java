package com.demiashkevich.movie.service;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.dao.CategoryDAO;
import com.demiashkevich.movie.entity.Category;

import java.util.List;

public class CategoryService extends AbstractService<Category, Integer> {

    public CategoryService(ProxyConnection connection) {
        super(connection);
    }

    @Override
    public List<Category> findAllItem() {
        return new CategoryDAO(connection).findAll();
    }

    @Override
    public boolean addItem(Category item) {
        return false;
    }

    @Override
    public Category findItem(Integer key) {
        return null;
    }

    @Override
    public List<Category> findItems(final int COUNT) {
        return null;
    }

    @Override
    public boolean deleteItem(long itemId) {
        return false;
    }
}
