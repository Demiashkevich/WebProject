package com.demiashkevich.movie.dao;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.Entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDAO<T extends Entity> implements DAO<T> {

    protected ProxyConnection connection;

    public AbstractDAO(ProxyConnection connection) {
        this.connection = connection;
    }

    List<T> findAll(final String REQUEST){
        List<T> list = null;
        try(PreparedStatement statement = connection.prepareStatement(REQUEST);
            ResultSet resultSet = statement.executeQuery()){
            list = this.parseResultSetLazy(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    List<T> find(final String REQUEST, final int FROM, final int COUNT){
        List<T> items = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(REQUEST)){
            statement.setInt(1, FROM);
            statement.setInt(2, COUNT);
            ResultSet resultSet = statement.executeQuery();
            items = this.parseResultSetLazy(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    int findCountRow(String REQUEST){
        try(PreparedStatement statement = connection.prepareStatement(REQUEST)){
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    boolean delete(final String REQUEST, final long ITEM_ID){
        try(PreparedStatement statement = connection.prepareStatement(REQUEST)) {
            statement.setLong(1, ITEM_ID);
            statement.executeUpdate();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    List<T> find(final String REQUEST, final long COUNT, boolean OCCUPANCY) {
        List<T> items = null;
        try(PreparedStatement statement = connection.prepareStatement(REQUEST)) {
            statement.setLong(1, COUNT);
            try(ResultSet resultSet = statement.executeQuery()){
                if(OCCUPANCY) {
                    items = parseResultSetFull(resultSet);
                } else {
                    items = parseResultSetLazy(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public abstract boolean addItem(T item);
    public abstract List<T> findAllItems();
    public abstract List<T> findItems(final int FROM, final int COUNT);
    public abstract int findCountRecords();
    public abstract boolean deleteItem(final long ITEM_ID);

    abstract List<T> parseResultSetFull(ResultSet resultSet);
    abstract List<T> parseResultSetLazy(ResultSet resultSet);

}
