package com.demiashkevich.movie.dao;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.Actor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActorDAO extends AbstractDAO<Actor> {

    private static final String SELECT_ALL = "SELECT actor.first_name, actor.last_name, actor.photo FROM actor";
    private static final String SELECT_LIMIT_BY_RATING = "SELECT actor.actor_id, actor.first_name, actor.last_name, actor.photo FROM actor ORDER BY actor.rating DESC LIMIT ?";
    private static final String INSERT_ACTOR = "INSERT INTO actor(first_name, last_name, biography, photo) VALUES (?,?,?,?)";
    private static final String SELECT_ACTOR_BY_ID = "SELECT actor.first_name, actor.last_name, actor.biography, actor.photo, movie.title, movie.poster FROM actor INNER JOIN movie_actor ON movie_actor.actor_id = actor.actor_id INNER JOIN movie ON movie_actor.movie_id = movie.movie_id";


    public ActorDAO(ProxyConnection connection) {
        super(connection);
    }

    @Override
    protected List<Actor> parseResultSet(ResultSet resultSet) {
        List<Actor> actors = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Actor actor = new Actor();
                actor.setActorId(resultSet.getInt(1));
                actor.setFirstName(resultSet.getString(2));
                actor.setLastName(resultSet.getString(3));
                actor.setPhoto(resultSet.getString(4));
                actors.add(actor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actors;
    }

    @Override
    public boolean addItem(Actor actor) {
        try(PreparedStatement statement = connection.prepareStatement(INSERT_ACTOR)) {
            statement.setString(1, actor.getFirstName());
            statement.setString(2, actor.getLastName());
            statement.setString(3, actor.getBiography());
            statement.setString(4, actor.getPhoto());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public String getSelectAll() {
        return SELECT_ALL;
    }

    @Override
    protected String getSelectLimitByRating() {
        return SELECT_LIMIT_BY_RATING;
    }
}
