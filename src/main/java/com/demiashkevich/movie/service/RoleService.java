package com.demiashkevich.movie.service;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.dao.RoleDAO;
import com.demiashkevich.movie.entity.Role;

import java.util.List;

public class RoleService extends AbstractService<Role, Integer> {

    public RoleService(ProxyConnection connection) {
        super(connection);
    }

    @Override
    public List<Role> findAllItem() {
        return new RoleDAO(connection).findAll();
    }

    @Override
    public boolean addItem(Role item) {
        return false;
    }

    @Override
    public Role findItem(Integer key) {
        return null;
    }

    @Override
    public List<Role> findItems(int COUNT) {
        return null;
    }

    @Override
    public boolean deleteItem(long itemId) {
        return false;
    }
}
