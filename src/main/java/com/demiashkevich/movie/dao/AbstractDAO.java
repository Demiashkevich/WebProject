package com.demiashkevich.movie.dao;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.Entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDAO<T extends Entity> implements DAO<T> {

    protected ProxyConnection connection;

    public AbstractDAO(ProxyConnection connection) {
        this.connection = connection;
    }

    @Override
      public List<T> findAll() {
        List<T> list = null;
        try(PreparedStatement statement = connection.prepareStatement(this.getSelectAll());
            ResultSet resultSet = statement.executeQuery()){
            list = this.parseResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<T> findSortByRating(final int COUNT) {
        List<T> list = null;
        try(PreparedStatement statement = connection.prepareStatement(this.getSelectLimitByRating())){
            statement.setInt(1, COUNT);
            try(ResultSet resultSet = statement.executeQuery()) {
                list = this.parseResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public T findItemById(long itemId){
       return null;
    }

    public abstract boolean addItem(T item);


    protected abstract List<T> parseResultSet(ResultSet resultSet);

    protected abstract String getSelectAll();
    protected abstract String getSelectLimitByRating();

}
