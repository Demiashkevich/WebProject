package com.demiashkevich.movie.service;


import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.Evaluation;
import com.demiashkevich.movie.entity.Movie;
import com.demiashkevich.movie.entity.User;
import com.demiashkevich.movie.exception.ServiceException;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class EvaluationServiceTest {

    private static final Logger LOGGER = Logger.getLogger(EvaluationServiceTest.class);

    private static final int EXIST = 0;

    @BeforeClass
    public static void initConnectionPool(){
        ConnectionPool.buildInstance(5);
    }

    @AfterClass
    public static void destroyConnectionPool(){
        ConnectionPool.close();
    }

    @Test
    public void addEvaluationExistCheck(){
        ProxyConnection connection = ConnectionPool.takeConnection();
        int resultActual = 0;
        int resultExpected = EXIST;
        try {
            //Initialize user object
            User user = new User();
            user.setUserId(12);
            user.setExperience(20);

            //Initialize movie object
            Movie movie = new Movie();
            movie.setMovieId(7);

            //Initialize evaluation object
            Evaluation evaluation = new Evaluation();
            evaluation.setMovie(movie);
            evaluation.setUser(user);
            evaluation.setTitle("Garry Potter");
            evaluation.setComment("Good movie");
            evaluation.setRating(8);

            EvaluationService evaluationService = new EvaluationService();

            resultActual = evaluationService.addEvaluation(evaluation);
        } catch (ServiceException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.putConnection(connection);
        }
        Assert.assertEquals(resultExpected, resultActual);
    }

}
