package com.demiashkevich.movie.dao;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.Role;
import com.demiashkevich.movie.exception.DAOException;

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
    protected List<Role> parseResultSetLazy(ResultSet resultSet) throws DAOException {
        List<Role> roles = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Role role = new Role();
                role.setRoleId(resultSet.getInt(1));
                role.setName(resultSet.getString(2));
                roles.add(role);
            }
        } catch (SQLException exception) {
            throw new DAOException(exception);
        }
        return roles;
    }

    @Override
    protected List<Role> parseResultSetFull(ResultSet resultSet) {
        return null;
    }

    @Override
    public boolean deleteItem(final long ROLE_ID) {
        return false;
    }

    @Override
    public boolean addItem(Role item) {
        return false;
    }

    @Override
    public List<Role> findItemsByMovieId(final long MOVIE_ID, final boolean OCCUPANCY) {
        return null;
    }

    @Override
    public List<Role> findItemsByActorId(final long ACTOR_ID, final boolean OCCUPANCY) {
        return null;
    }

    @Override
    public List<Role> findAllItems() throws DAOException {
        return this.findAll(SELECT_ALL_ROLE);
    }

    @Override
    public List<Role> findItems(final int FROM, final int COUNT) {
        return null;
    }

    @Override
    public int findCountRecords() {
        return 0;
    }

}
