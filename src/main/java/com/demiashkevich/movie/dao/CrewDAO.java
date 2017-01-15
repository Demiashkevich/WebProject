package com.demiashkevich.movie.dao;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.Crew;
import com.demiashkevich.movie.entity.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CrewDAO extends AbstractDAO<Crew> {

    private static final String SELECT_ALL_CREW = "SELECT person.person_id, person.first_name, person.last_name FROM person";
    private static final String SELECT_CREW_BY_MOVIE_ID = "SELECT person.person_id, person.first_name, person.last_name, role.role_id, role.name FROM movie_person_role INNER JOIN person ON movie_person_role.person_id = person.person_id INNER JOIN role ON movie_person_role.role_id = role.role_id WHERE movie_person_role.movie_id = ?";

    public CrewDAO(ProxyConnection connection) {
        super(connection);
    }

    @Override
    public boolean addItem(Crew item) {
        return false;
    }

    @Override
    protected List<Crew> parseResultSetFull(ResultSet resultSet) {
        List<Crew> crews = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Crew crew = new Crew();
                crew.setCrewId(resultSet.getInt(1));
                crew.setFirstName(resultSet.getString(2));
                crew.setLastName(resultSet.getString(3));
                Role role = new Role();
                role.setRoleId(resultSet.getInt(4));
                role.setName(resultSet.getString(5));
                crew.setRole(role);
                crews.add(crew);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return crews;
    }

    @Override
    protected List<Crew> parseResultSetLazy(ResultSet resultSet) {
        List<Crew> crews = new ArrayList<>();
        try {
            while(resultSet.next()){
                Crew crew = new Crew();
                crew.setCrewId(resultSet.getInt(1));
                crew.setFirstName(resultSet.getString(2));
                crew.setLastName(resultSet.getString(3));
                crews.add(crew);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return crews;
    }

    @Override
    protected String getSelectItemAll() {
        return SELECT_ALL_CREW;
    }

    @Override
    protected String getSelectItemLimitByRating() {
        return null;
    }

    @Override
    protected String getSelectItemByMovieId() {
        return SELECT_CREW_BY_MOVIE_ID;
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
