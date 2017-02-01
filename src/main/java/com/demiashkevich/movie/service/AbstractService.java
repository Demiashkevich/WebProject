package com.demiashkevich.movie.service;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.Entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractService {

    protected ProxyConnection connection;
    protected static final boolean FULL_OCCUPANCY = true;
    protected static final boolean LAZY_OCCUPANCY = false;

    public AbstractService() {}


    /**Using the functions set. Transformation list to set, and conversely, back to list
     * @param items list object to convert
     * @param <T>
     * @return list transformation list
     */
    protected <T extends Entity> List<T> containListItems(List<T> items){
        Set<T> sendList = new HashSet<>();
        sendList.addAll(items);
        List<T> results = new ArrayList<>();
        results.addAll(sendList);
        return results;
    }

}
