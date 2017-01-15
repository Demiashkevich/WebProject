package com.demiashkevich.movie.dao;

import java.util.List;

public interface DAO<T> {

    public List<T> findAll();
    public List<T> find(final int FROM, final int COUNT);
    public int findCountRow();
    public List<T> findSortByRating(final int COUNT);
    public boolean addItem(T item);
    public List<T> findItemsByMovieId(final long MOVIE_ID, boolean occupancy);
    public List<T> findItemsByActorId(final long ACTOR_ID, boolean occupancy);
    public boolean deleteItem(long itemId);

}
