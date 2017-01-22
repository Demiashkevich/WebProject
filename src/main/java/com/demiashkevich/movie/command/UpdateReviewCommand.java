package com.demiashkevich.movie.command;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.Evaluation;
import com.demiashkevich.movie.entity.Movie;
import com.demiashkevich.movie.entity.User;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.EvaluationService;

import javax.servlet.http.HttpServletRequest;

public class UpdateReviewCommand implements Command {

    private static final String PAGE_SUCCESS = "path.page.success";
    private static final String PAGE_ERROR = "path.page.error";

    @Override
    public String execute(HttpServletRequest request) {
        ProxyConnection connection = null;
        try {
            connection = ConnectionPool.takeConnection();

            Evaluation evaluation = new Evaluation();
            evaluation.setTitle(request.getParameter("title"));
            evaluation.setComment(request.getParameter("comment"));
            evaluation.setRating(Double.parseDouble(request.getParameter("rating")));
            Movie movie = new Movie();
            movie.setMovieId(Integer.parseInt(request.getParameter("movie_id")));
            evaluation.setMovie(movie);
            User user = new User();
            user.setUserId(Integer.parseInt(request.getParameter("user_id")));
            evaluation.setUser(user);

            EvaluationService evaluationService = new EvaluationService(connection);
            if(evaluationService.updateEvaluation(evaluation)){
                return ConfigurationManager.getKey(PAGE_SUCCESS);
            } else {
                return ConfigurationManager.getKey(PAGE_ERROR);
            }
        } finally {
            ConnectionPool.putConnection(connection);
        }
    }

}
