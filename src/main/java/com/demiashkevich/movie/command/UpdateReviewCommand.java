package com.demiashkevich.movie.command;

import com.demiashkevich.movie.entity.Evaluation;
import com.demiashkevich.movie.entity.Movie;
import com.demiashkevich.movie.entity.User;
import com.demiashkevich.movie.exception.ServiceException;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.EvaluationService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class UpdateReviewCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(UpdateReviewCommand.class);

    private static final String PAGE_SUCCESS = "path.page.success";
    private static final String PAGE_ERROR = "path.page.error";
    private static final String PAGE_ERROR_UPDATE_REVIEW = "";

    @Override
    public String execute(HttpServletRequest request) {
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
        try {
            EvaluationService evaluationService = new EvaluationService();
            if(evaluationService.updateEvaluation(evaluation)){
                return ConfigurationManager.getKey(PAGE_SUCCESS);
            } else {
                return ConfigurationManager.getKey(PAGE_ERROR);
            }
        } catch (ServiceException exception) {
            LOGGER.error(exception);
            request.setAttribute("error", exception);
            return ConfigurationManager.getKey(PAGE_ERROR_UPDATE_REVIEW);
        }
    }

}
