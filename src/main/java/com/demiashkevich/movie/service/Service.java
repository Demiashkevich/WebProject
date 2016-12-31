package com.demiashkevich.movie.service;

import com.demiashkevich.movie.entity.Entity;

import java.util.List;

public interface Service<T extends Entity, K> {

    public boolean addItem(T item);
    public T getItem(K key);
    public List<T> getItems(final int COUNT);

}
