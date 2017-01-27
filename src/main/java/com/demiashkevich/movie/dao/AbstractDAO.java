package com.demiashkevich.movie.dao;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.Entity;
import com.demiashkevich.movie.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDAO<T extends Entity> implements DAO<T> {

    protected ProxyConnection connection;

    public AbstractDAO(ProxyConnection connection) {
        this.connection = connection;
    }

    List<T> findAll(final String REQUEST) throws DAOException {
        List<T> list;
        try(PreparedStatement statement = connection.prepareStatement(REQUEST);
            ResultSet resultSet = statement.executeQuery()){
            list = this.parseResultSetLazy(resultSet);
        } catch (SQLException exception) {
            throw new DAOException(exception);
        }
        return list;
    }

    List<T> find(final String REQUEST, final int FROM, final int COUNT) throws DAOException {
        List<T> items;
        try(PreparedStatement statement = connection.prepareStatement(REQUEST)){
            statement.setInt(1, FROM);
            statement.setInt(2, COUNT);
            ResultSet resultSet = statement.executeQuery();
            items = this.parseResultSetLazy(resultSet);
        } catch (SQLException exception) {
            throw new DAOException(exception);
        }
        return items;
    }

    int findCountRow(String REQUEST) throws DAOException {
        try(PreparedStatement statement = connection.prepareStatement(REQUEST)){
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException exception) {
            throw new DAOException(exception);
        }
        return 0;
    }

    boolean delete(final String REQUEST, final long ITEM_ID) throws DAOException {
        try(PreparedStatement statement = connection.prepareStatement(REQUEST)) {
            statement.setLong(1, ITEM_ID);
            statement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            throw new DAOException(exception);
        }
    }

    List<T> find(final String REQUEST, final long COUNT, boolean OCCUPANCY) throws DAOException {
        List<T> items;
        try(PreparedStatement statement = connection.prepareStatement(REQUEST)) {
            statement.setLong(1, COUNT);
            try(ResultSet resultSet = statement.executeQuery()){
                if(OCCUPANCY) {
                    items = parseResultSetFull(resultSet);
                } else {
                    items = parseResultSetLazy(resultSet);
                }
            }
        } catch (SQLException exception) {
            throw new DAOException(exception);
        }
        return items;
    }

    public abstract boolean addItem(T item) throws DAOException;
    public abstract List<T> findAllItems() throws DAOException;
    public abstract List<T> findItems(final int FROM, final int COUNT) throws DAOException;
    public abstract int findCountRecords() throws DAOException;
    public abstract boolean deleteItem(final long ITEM_ID) throws DAOException;

    abstract List<T> parseResultSetFull(ResultSet resultSet) throws DAOException;
    abstract List<T> parseResultSetLazy(ResultSet resultSet) throws DAOException;

}
