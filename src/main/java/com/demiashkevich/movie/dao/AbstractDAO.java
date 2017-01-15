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

    @Override
    public List<T> findAll() {
        List<T> list = null;
        try(PreparedStatement statement = connection.prepareStatement(getSelectItemAll());
            ResultSet resultSet = statement.executeQuery()){
            list = this.parseResultSetLazy(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<T> find(final int FROM, final int COUNT){
        List<T> items = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(getSelectItemLimit())){
            statement.setInt(1, FROM);
            statement.setInt(2, COUNT);
            ResultSet resultSet = statement.executeQuery();
            items = this.parseResultSetLazy(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public int findCountRow(){
        try(PreparedStatement statement = connection.prepareStatement(getSelectNumberRowItem())){
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<T> findSortByRating(final int COUNT) {
        return this.findWithParameters(COUNT, getSelectItemLimitByRating(), false);
    }

    public List<T> findItemsByMovieId(long movieId, boolean occupancy){
        return this.findWithParameters(movieId, getSelectItemByMovieId(), occupancy);
    }

    public List<T> findItemsByActorId(long actorId, boolean occupancy){
        return this.findWithParameters(actorId, getSelectItemByActorId(), occupancy);
    }

    @Override
    public boolean deleteItem(long itemId){
        try(PreparedStatement statement = connection.prepareStatement(getDeleteItemById())) {
            statement.setLong(1, itemId);
            statement.executeUpdate();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    private List<T> findWithParameters(long count, String request, boolean occupancy) {
        List<T> items = null;
        try(PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setLong(1, count);
            try(ResultSet resultSet = statement.executeQuery()){
                if(occupancy) {
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

    protected abstract List<T> parseResultSetFull(ResultSet resultSet);
    protected abstract List<T> parseResultSetLazy(ResultSet resultSet);

    protected abstract String getSelectItemAll();
    protected abstract String getSelectItemLimitByRating();
    protected abstract String getSelectItemByMovieId();
    protected abstract String getSelectItemByActorId();
    protected abstract String getDeleteItemById();
    protected abstract String getSelectItemLimit();
    protected abstract String getSelectNumberRowItem();

}
