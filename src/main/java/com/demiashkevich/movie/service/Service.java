package com.demiashkevich.movie.service;

import com.demiashkevich.movie.entity.Entity;

import java.util.List;

public interface Service<T extends Entity, K> {

    public List<T> findAllItem();
    public boolean addItem(T item);
    public T findItem(K key);
    public List<T> findItems(final int COUNT);
    public boolean deleteItem(long itemId);

}
