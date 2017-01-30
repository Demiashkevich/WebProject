package com.demiashkevich.movie.service;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.dao.EvaluationDAO;
import com.demiashkevich.movie.dao.MovieDAO;
import com.demiashkevich.movie.dao.UserDAO;
import com.demiashkevich.movie.entity.Evaluation;
import com.demiashkevich.movie.entity.User;
import com.demiashkevich.movie.exception.DAOException;
import com.demiashkevich.movie.exception.ServiceException;
import com.demiashkevich.movie.validation.EvaluationValidation;
import com.demiashkevich.movie.validation.Validation;

public class EvaluationService extends AbstractService {

    private static final String EXPERIENCE = "count.add.experience";

    private static final int RATING_INTERVAL = 2;

    private static final int ERROR_VALIDATION = -1;
    private static final int ERROR_EXIST = 0;
    private static final int SUCCESS = 1;

    public EvaluationService() {}

    public int addEvaluation(Evaluation evaluation, User user) throws ServiceException {
        try {
            connection = ConnectionPool.takeConnection();

            EvaluationDAO evaluationDAO = new EvaluationDAO(connection);
            long movieId = evaluation.getMovie().getMovieId();
            int userId = evaluation.getUser().getUserId();
            Validation<Evaluation> validation = new EvaluationValidation();
            if(!validation.execute(evaluation)){
                return ERROR_VALIDATION;
            }
            if(evaluationDAO.checkExistEvaluation(movieId, userId)) {
                return ERROR_EXIST;
            }
            int countEvaluation = evaluationDAO.findCountRecordsByMovieId(evaluation.getMovie().getMovieId());
            UserDAO userDAO = new UserDAO(connection);
            if(countEvaluation <= 2){
                userDAO.updateExperience(user.getUserId(), 20);
            } else {
                MovieDAO movieDAO = new MovieDAO(connection);
                double rating = movieDAO.findRatingByMovieId(movieId);
                if(rating - RATING_INTERVAL <= evaluation.getRating() && evaluation.getRating() >= rating + RATING_INTERVAL){
                    userDAO.updateExperience(user.getUserId(), 20);
                } else {
                    userDAO.updateExperience(user.getUserId(), 20 / 2);
                }
            }
            evaluationDAO.addItem(evaluation);
            evaluationDAO.updateRating(evaluation);
        } catch (DAOException exception) {
            throw new ServiceException(exception);
        } finally {
            ConnectionPool.putConnection(connection);
        }
        return SUCCESS;
    }

    public boolean updateEvaluation(Evaluation evaluation) throws ServiceException {
        try {
            connection = ConnectionPool.takeConnection();
            Validation<Evaluation> validation = new EvaluationValidation();
            EvaluationDAO evaluationDAO = new EvaluationDAO(connection);
            if(!validation.execute(evaluation)){
                return false;
            }
            evaluationDAO.updateItem(evaluation);
            evaluationDAO.updateRating(evaluation);
        } catch (DAOException exception) {
            throw new ServiceException(exception);
        } finally {
            ConnectionPool.putConnection(connection);
        }
        return true;
    }

    public boolean deleteEvaluation(int userId, long movieId) throws ServiceException {
        try {
            connection = ConnectionPool.takeConnection();

            EvaluationDAO evaluationDAO = new EvaluationDAO(connection);
            evaluationDAO.deleteItem(userId, movieId);
            evaluationDAO.updateRating(movieId);
            UserDAO userDAO = new UserDAO(connection);
            userDAO.updateExperience(userId, -20);
        } catch (DAOException exception) {
            throw new ServiceException(exception);
        } finally {
            ConnectionPool.putConnection(connection);
        }
        return true;
    }
}
