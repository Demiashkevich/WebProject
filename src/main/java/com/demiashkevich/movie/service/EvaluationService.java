package com.demiashkevich.movie.service;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.dao.EvaluationDAO;
import com.demiashkevich.movie.entity.Evaluation;

public class EvaluationService extends AbstractService {

    public EvaluationService(ProxyConnection connection) {
        super(connection);
    }

    public boolean addEvaluation(Evaluation item) {
        EvaluationDAO evaluationDAO = new EvaluationDAO(connection);
        if(!evaluationDAO.checkExistEvaluation(item.getMovie().getMovieId(), item.getUser().getUserId())) {
            if (evaluationDAO.addItem(item)) {
                evaluationDAO.updateRating(item);
                return true;
            }
        }
        return false;
    }

    public boolean updateEvaluation(Evaluation evaluation){
        EvaluationDAO evaluationDAO = new EvaluationDAO(connection);
        if(evaluationDAO.updateItem(evaluation)){
            evaluationDAO.updateRating(evaluation);
            return true;
        }
        return false;
    }

    public boolean deleteEvaluation(long userId, long movieId){
        EvaluationDAO evaluationDAO = new EvaluationDAO(connection);
        if(evaluationDAO.deleteItem(userId, movieId)) {
            evaluationDAO.updateRating(movieId);
            return true;
        }
        return false;
    }
}
