package com.demiashkevich.movie.dao;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.Actor;
import com.demiashkevich.movie.entity.Movie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActorDAO extends AbstractDAO<Actor> {

    private static final String SELECT_ALL_ACTOR = "SELECT actor.actor_id, actor.first_name, actor.last_name, actor.photo FROM actor";
    private static final String SELECT_ACTOR_LIMIT = "SELECT actor.actor_id, actor.first_name, actor.last_name, actor.photo FROM actor LIMIT ?,?";
    private static final String SELECT_NUMBER_ROW_ACTOR = "SELECT COUNT(*) FROM actor";
    private static final String SELECT_LIMIT_BY_RATING = "SELECT actor.actor_id, actor.first_name, actor.last_name, actor.photo FROM actor LIMIT ?";
    private static final String INSERT_ACTOR = "INSERT INTO actor(first_name, last_name, biography, photo) VALUES (?,?,?,?)";
    private static final String INSERT_ACTOR_MOVIE = "INSERT INTO movie_actor(movie_id, actor_id) VALUES (?,LAST_INSERT_ID())";
    private static final String DELETE_ACTOR = "DELETE FROM actor WHERE actor.actor_id = ?";
    private static final String SELECT_ACTOR_BY_MOVIE_ID = "SELECT actor.actor_id, actor.first_name, actor.last_name, actor.photo FROM actor INNER JOIN movie_actor ON actor.actor_id = movie_actor.actor_id WHERE movie_actor.movie_id = ?";
    private static final String SELECT_ACTOR_BY_ACTOR_ID = "SELECT actor.actor_id, actor.first_name, actor.last_name, actor.biography, actor.photo FROM actor WHERE actor.actor_id = ?";

    public ActorDAO(ProxyConnection connection) {
        super(connection);
    }

    @Override
    protected List<Actor> parseResultSetLazy(ResultSet resultSet) {
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
    protected List<Actor> parseResultSetFull(ResultSet resultSet) {
        List<Actor> actors = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Actor actor = new Actor();
                actor.setActorId(resultSet.getInt(1));
                actor.setFirstName(resultSet.getString(2));
                actor.setLastName(resultSet.getString(3));
                actor.setBiography(resultSet.getString(4));
                actor.setPhoto(resultSet.getString(5));
                actors.add(actor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actors;
    }

    @Override
    public boolean addItem(Actor actor) {
        try {
            connection.setAutoCommit(false);
            try(PreparedStatement statementActor = connection.prepareStatement(INSERT_ACTOR)) {
                statementActor.setString(1, actor.getFirstName());
                statementActor.setString(2, actor.getLastName());
                statementActor.setString(3, actor.getBiography());
                statementActor.setString(4, actor.getPhoto());
                statementActor.executeUpdate();
                for(Movie movie : actor.getMovies()){
                    try(PreparedStatement statementMovie = connection.prepareStatement(INSERT_ACTOR_MOVIE)){
                        statementMovie.setLong(1, movie.getMovieId());
                        statementMovie.executeUpdate();
                    }
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public String getSelectItemAll() {
        return SELECT_ALL_ACTOR;
    }

    @Override
    protected String getSelectItemLimitByRating() {
        return SELECT_LIMIT_BY_RATING;
    }

    @Override
    protected String getSelectItemByMovieId() {
        return SELECT_ACTOR_BY_MOVIE_ID;
    }

    @Override
    protected String getSelectItemByActorId() {
        return SELECT_ACTOR_BY_ACTOR_ID;
    }

    @Override
    protected String getDeleteItemById() {
        return DELETE_ACTOR;
    }

    @Override
    protected String getSelectItemLimit() {
        return SELECT_ACTOR_LIMIT;
    }

    @Override
    protected String getSelectNumberRowItem() {
        return SELECT_NUMBER_ROW_ACTOR;
    }
}
