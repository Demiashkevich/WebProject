package com.demiashkevich.movie.dao;

import java.util.List;

public interface DAO<T> {

    public List<T> findAll();
    public List<T> findSortByRating(int count);
    public boolean addItem(T item);
    public T findItemById(long itemId);

}
