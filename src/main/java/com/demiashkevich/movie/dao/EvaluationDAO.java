package com.demiashkevich.movie.dao;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.Evaluation;
import com.demiashkevich.movie.entity.User;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EvaluationDAO extends AbstractDAO<Evaluation>{

    private static final String INSERT_EVALUATION = "INSERT INTO evaluation(user_id, movie_id, title, comment, rating) VALUES(?,?,?,?,?)";
    private static final String SELECT_EVALUATION_BY_MOVIE_ID = "SELECT evaluation.title, evaluation.comment, evaluation.rating, user.user_id, user.first_name, user.last_name FROM evaluation INNER JOIN user ON evaluation.user_id = user.user_id WHERE evaluation.movie_id = ?";
    private static final String DELETE_EVALUATION = "DELETE FROM evaluation WHERE evaluation.user_id = ? AND evaluation.movie_id = ?";
    private static final String UPDATE_EVALUATION = "UPDATE evaluation SET evaluation.title = ?, evaluation.comment = ?, evaluation.rating = ? WHERE evaluation.user_id = ? AND evaluation.movie_id = ?";
    private static final String CALL_PROCEDURE_UPDATE_RATING = "{CALL update_rating(?)}";

    public EvaluationDAO(ProxyConnection connection) {
        super(connection);
    }

    public boolean deleteItem(long userId, long movieId){
        try(PreparedStatement statement = connection.prepareStatement(DELETE_EVALUATION)) {
            statement.setLong(1, userId);
            statement.setLong(2, movieId);
            statement.executeUpdate();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addItem(Evaluation item) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EVALUATION)){
            preparedStatement.setInt(1, item.getUser().getUserId());
            preparedStatement.setLong(2, item.getMovie().getMovieId());
            preparedStatement.setString(3, item.getTitle());
            preparedStatement.setString(4, item.getComment());
            preparedStatement.setDouble(5, item.getRating());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateItem(Evaluation evaluation){
        try(PreparedStatement statement = connection.prepareStatement(UPDATE_EVALUATION)) {
            statement.setString(1, evaluation.getTitle());
            statement.setString(2, evaluation. getComment());
            statement.setDouble(3, evaluation.getRating());
            statement.setInt(4, evaluation.getUser().getUserId());
            statement.setLong(5, evaluation.getMovie().getMovieId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateRating(Evaluation evaluation){
        try(CallableStatement statement = connection.prepareCall(CALL_PROCEDURE_UPDATE_RATING)) {
            statement.setLong(1, evaluation.getMovie().getMovieId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateRating(long movieId){
        try(CallableStatement statement = connection.prepareCall(CALL_PROCEDURE_UPDATE_RATING)) {
            statement.setLong(1, movieId);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected List<Evaluation> parseResultSetLazy(ResultSet resultSet) {
        return null;
    }

    @Override
    protected List<Evaluation> parseResultSetFull(ResultSet resultSet) {
        List<Evaluation> evaluations = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Evaluation evaluation = new Evaluation();
                User user = new User();
                evaluation.setTitle(resultSet.getString(1));
                evaluation.setComment(resultSet.getString(2));
                evaluation.setRating(resultSet.getDouble(3));
                user.setUserId(resultSet.getInt(4));
                user.setFirstName(resultSet.getString(5));
                user.setLastName(resultSet.getString(6));
                evaluation.setUser(user);
                evaluations.add(evaluation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return evaluations;
    }

    @Override
    protected String getSelectItemAll() {
        return null;
    }

    @Override
    protected String getSelectItemLimitByRating() {
        return null;
    }

    @Override
    protected String getSelectItemByMovieId() {
        return SELECT_EVALUATION_BY_MOVIE_ID;
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
