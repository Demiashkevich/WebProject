package com.demiashkevich.movie.dao;

import com.demiashkevich.movie.entity.Entity;
import com.demiashkevich.movie.exception.DAOException;

import java.util.List;

public interface DAO<T extends Entity> {

    public List<T> findAllItems() throws DAOException;
    public List<T> findItems(final int FROM, final int COUNT) throws DAOException;
    public int findCountRecords() throws DAOException;
    public boolean addItem(T item) throws DAOException;
    public List<T> findItemsByMovieId(final long MOVIE_ID, final boolean OCCUPANCY) throws DAOException;
    public List<T> findItemsByActorId(final long ACTOR_ID, final boolean OCCUPANCY) throws DAOException;
    public boolean deleteItem(final long ITEM_ID) throws DAOException;

}
