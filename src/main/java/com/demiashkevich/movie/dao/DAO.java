package com.demiashkevich.movie.dao;

import java.util.List;

public interface DAO<T> {

    public List<T> findAllItems();
    public List<T> findItems(final int FROM, final int COUNT);
    public int findCountRecords();
    public boolean addItem(T item);
    public List<T> findItemsByMovieId(final long MOVIE_ID, final boolean OCCUPANCY);
    public List<T> findItemsByActorId(final long ACTOR_ID, final boolean OCCUPANCY);
    public boolean deleteItem(final long ITEM_ID);

}
