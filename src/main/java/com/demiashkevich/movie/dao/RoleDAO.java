package com.demiashkevich.movie.dao;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO extends AbstractDAO<Role> {

    private static final String SELECT_ALL_ROLE = "SELECT role.role_id, role.name FROM role";

    public RoleDAO(ProxyConnection connection) {
        super(connection);
    }

    @Override
    public boolean deleteItem(long itemId) {
        return false;
    }

    @Override
    public boolean addItem(Role item) {
        return false;
    }

    @Override
    protected List<Role> parseResultSetLazy(ResultSet resultSet) {
        List<Role> roles = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Role role = new Role();
                role.setRoleId(resultSet.getInt(1));
                role.setName(resultSet.getString(2));
                roles.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    @Override
    protected List<Role> parseResultSetFull(ResultSet resultSet) {
        return null;
    }

    @Override
    protected String getSelectItemAll() {
        return SELECT_ALL_ROLE;
    }

    @Override
    protected String getSelectItemLimitByRating() {
        return null;
    }

    @Override
    protected String getSelectItemByMovieId() {
        return null;
    }

    @Override
    protected String getSelectItemByActorId() {
        return null;
    }

    @Override
    protected String getDeleteItemById() {
        return null;
    }

    @Override
    protected String getSelectItemLimit() {
        return null;
    }

    @Override
    protected String getSelectNumberRowItem() {
        return null;
    }

}
