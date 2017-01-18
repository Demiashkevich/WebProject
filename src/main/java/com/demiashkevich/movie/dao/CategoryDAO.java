package com.demiashkevich.movie.dao;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO extends AbstractDAO<Category> {

    private static final String SELECT_ALL_CATEGORY = "SELECT category.category_id, category.name FROM category";
    private static final String SELECT_CATEGORY_BY_MOVIE_ID = "SELECT category.category_id, category.name FROM category INNER JOIN category_movie ON category.category_id = category_movie.category_id WHERE category_movie.movie_id = ?";

    public CategoryDAO(ProxyConnection connection) {
        super(connection);
    }

    @Override
    public boolean addItem(Category item) {
        return false;
    }

    @Override
    protected List<Category> parseResultSetFull(ResultSet resultSet) {
        return null;
    }

    @Override
    protected List<Category> parseResultSetLazy(ResultSet resultSet) {
        List<Category> categories = new ArrayList<>();
        try {
            while(resultSet.next()){
                Category category = new Category();
                category.setCategoryId(resultSet.getInt(1));
                category.setName(resultSet.getString(2));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    protected String getSelectItemAll() {
        return SELECT_ALL_CATEGORY;
    }

    @Override
    protected String getSelectItemLimitByRating() {
        return null;
    }

    @Override
    protected String getSelectItemByMovieId() {
        return SELECT_CATEGORY_BY_MOVIE_ID;
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
