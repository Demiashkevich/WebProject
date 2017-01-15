package com.demiashkevich.movie.service;

import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.dao.EvaluationDAO;
import com.demiashkevich.movie.entity.Evaluation;

import java.util.List;

public class EvaluationService extends AbstractService<Evaluation, Integer> {

    public EvaluationService(ProxyConnection connection) {
        super(connection);
    }

    @Override
    public List<Evaluation> findAllItem() {
        return null;
    }

    @Override
    public boolean addItem(Evaluation item) {
        EvaluationDAO evaluationDAO = new EvaluationDAO(connection);
        if(evaluationDAO.addItem(item)){
            evaluationDAO.updateRating(item);
            return true;
        }
        return false;
    }

    @Override
    public Evaluation findItem(Integer key) {
        return null;
    }

    @Override
    public List<Evaluation> findItems(int COUNT) {
        return null;
    }

    @Override
    public boolean deleteItem(long itemId) {
        return false;
    }

    public boolean updateItem(Evaluation evaluation){
        EvaluationDAO evaluationDAO = new EvaluationDAO(connection);
        if(evaluationDAO.updateItem(evaluation)){
            evaluationDAO.updateRating(evaluation);
            return true;
        }
        return false;
    }

    public boolean deleteItem(long userId, long movieId){
        EvaluationDAO evaluationDAO = new EvaluationDAO(connection);
        if(evaluationDAO.deleteItem(userId, movieId)) {
            evaluationDAO.updateRating(movieId);
            return true;
        }
        return false;
    }
}
