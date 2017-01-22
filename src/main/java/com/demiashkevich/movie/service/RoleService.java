package com.demiashkevich.movie.service;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.dao.RoleDAO;
import com.demiashkevich.movie.entity.Role;

import java.util.List;

public class RoleService extends AbstractService {

    public RoleService(ProxyConnection connection) {
        super(connection);
    }

    public List<Role> findAllRoles() {
        return new RoleDAO(connection).findAllItems();
    }

}
