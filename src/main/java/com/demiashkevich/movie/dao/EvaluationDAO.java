package com.demiashkevich.movie.dao;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.Evaluation;
import com.demiashkevich.movie.entity.User;
import com.demiashkevich.movie.exception.DAOException;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EvaluationDAO extends AbstractDAO<Evaluation>{

    private static final String INSERT_EVALUATION = "INSERT INTO evaluation(user_id, movie_id, title, comment, rating) VALUES(?,?,?,?,?)";
    private static final String CHECK_EXIST_EVALUATION = "SELECT 1 FROM evaluation WHERE evaluation.movie_id = ? AND evaluation.user_id = ? LIMIT 1";
    private static final String SELECT_EVALUATION_BY_MOVIE_ID = "SELECT evaluation.title, evaluation.comment, evaluation.rating, user.user_id, user.first_name, user.last_name FROM evaluation INNER JOIN user ON evaluation.user_id = user.user_id WHERE evaluation.movie_id = ?";
    private static final String DELETE_EVALUATION = "DELETE FROM evaluation WHERE evaluation.user_id = ? AND evaluation.movie_id = ?";
    private static final String UPDATE_EVALUATION = "UPDATE evaluation SET evaluation.title = ?, evaluation.comment = ?, evaluation.rating = ? WHERE evaluation.user_id = ? AND evaluation.movie_id = ?";
    private static final String CALL_PROCEDURE_UPDATE_RATING = "{CALL update_rating(?)}";

    public EvaluationDAO(ProxyConnection connection) {
        super(connection);
    }

    public boolean deleteItem(final long USER_ID, final long MOVIE_ID) throws DAOException {
        try(PreparedStatement statement = connection.prepareStatement(DELETE_EVALUATION)) {
            statement.setLong(1, USER_ID);
            statement.setLong(2, MOVIE_ID);
            statement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            throw new DAOException(exception);
        }
    }

    @Override
    public boolean addItem(Evaluation item) throws DAOException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EVALUATION)){
            preparedStatement.setInt(1, item.getUser().getUserId());
            preparedStatement.setLong(2, item.getMovie().getMovieId());
            preparedStatement.setString(3, item.getTitle());
            preparedStatement.setString(4, item.getComment());
            preparedStatement.setDouble(5, item.getRating());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            throw new DAOException(exception);
        }
    }

    @Override
    public List<Evaluation> findItemsByMovieId(final long MOVIE_ID, final boolean OCCUPANCY) throws DAOException {
        return this.find(SELECT_EVALUATION_BY_MOVIE_ID, MOVIE_ID, OCCUPANCY);
    }

    public boolean checkExistEvaluation(final long MOVIE_ID, final int USER_ID) throws DAOException {
        try(PreparedStatement statement = connection.prepareStatement(CHECK_EXIST_EVALUATION)){
            statement.setLong(1,MOVIE_ID);
            statement.setInt(2, USER_ID);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                return true;
            }
        } catch (SQLException exception) {
            throw new DAOException(exception);
        }
        return false;
    }

    @Override
    public List<Evaluation> findItemsByActorId(final long ACTOR_ID, final boolean OCCUPANCY) {
        return null;
    }

    @Override
    public List<Evaluation> findAllItems() {
        return null;
    }

    @Override
    public List<Evaluation> findItems(final int FROM, final int COUNT) {
        return null;
    }

    @Override
    public int findCountRecords() {
        return 0;
    }

    @Override
    public boolean deleteItem(final long EVALUATION_ID) {
        return false;
    }

    public boolean updateItem(Evaluation evaluation) throws DAOException {
        try(PreparedStatement statement = connection.prepareStatement(UPDATE_EVALUATION)) {
            statement.setString(1, evaluation.getTitle());
            statement.setString(2, evaluation. getComment());
            statement.setDouble(3, evaluation.getRating());
            statement.setInt(4, evaluation.getUser().getUserId());
            statement.setLong(5, evaluation.getMovie().getMovieId());
            statement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            throw new DAOException(exception);
        }
    }

    public boolean updateRating(Evaluation evaluation) throws DAOException {
        try(CallableStatement statement = connection.prepareCall(CALL_PROCEDURE_UPDATE_RATING)) {
            statement.setLong(1, evaluation.getMovie().getMovieId());
            statement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            throw new DAOException(exception);
        }
    }

    public boolean updateRating(final long MOVIE_ID) throws DAOException {
        try(CallableStatement statement = connection.prepareCall(CALL_PROCEDURE_UPDATE_RATING)) {
            statement.setLong(1, MOVIE_ID);
            statement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            throw new DAOException(exception);
        }
    }

    @Override
    protected List<Evaluation> parseResultSetLazy(ResultSet resultSet) {
        return null;
    }

    @Override
    protected List<Evaluation> parseResultSetFull(ResultSet resultSet) throws DAOException {
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
        } catch (SQLException exception) {
           throw new DAOException(exception);
        }
        return evaluations;
    }

}
