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


    /**
     * @param REQUEST sql
     * @return list object on type <T extends Entity>
     * @throws DAOException
     */
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


    /**
     * @param REQUEST sql
     * @param FROM - point start sample
     * @param COUNT - number count object selected at a time
     * @return list object on type <T extends Entity>
     * @throws DAOException
     */
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


    /**
     * @param REQUEST sql
     * @return number count records in table, if they don't exist return 0
     * @throws DAOException
     */
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


    /**
     * @param REQUEST sql
     * @param ITEM_ID - key for delete
     * @return true if delete success or throw DAOException
     * @throws DAOException
     */
    boolean delete(final String REQUEST, final long ITEM_ID) throws DAOException {
        try(PreparedStatement statement = connection.prepareStatement(REQUEST)) {
            statement.setLong(1, ITEM_ID);
            statement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            throw new DAOException(exception);
        }
    }


    /**
     * @param REQUEST sql
     * @param COUNT - number count object selected at a time
     * @param OCCUPANCY - occupancy object. If true will call full parse result set, if false lazy parse result set
     * @return list object on type <T extends Entity>
     * @throws DAOException
     */
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


    /**Add item in DB
     * @param item object type <T extends Entity>
     * @return true if add success or throw DAOException
     * @throws DAOException
     */
    public abstract boolean addItem(T item) throws DAOException;

    /**Find all records
     * @return list object type <T extends Entity>
     * @throws DAOException
     */
    public abstract List<T> findAllItems() throws DAOException;

    /**Find item limited number of COUNT
     * @param FROM - point start sample
     * @param COUNT - number count object selected at a time
     * @return list object type <T extends Entity>
     * @throws DAOException
     */
    public abstract List<T> findItems(final int FROM, final int COUNT) throws DAOException;

    /**
     * @return number count records in table, if they don't exist return 0
     * @throws DAOException
     */
    public abstract int findCountRecords() throws DAOException;

    /**
     * @param ITEM_ID - key for delete
     * @return true if delete success or DAOException if not
     * @throws DAOException
     */
    public abstract boolean deleteItem(final long ITEM_ID) throws DAOException;

    /**Parse result set and fill all field in object
     * @param resultSet table result from DB
     * @return list object type <T extends Entity>
     * @throws DAOException
     */
    abstract List<T> parseResultSetFull(ResultSet resultSet) throws DAOException;

    /**Parse result set and fill some field in object
     * @param resultSet table result from DB
     * @return list object type <T extends Entity>
     * @throws DAOException
     */
    abstract List<T> parseResultSetLazy(ResultSet resultSet) throws DAOException;

}
