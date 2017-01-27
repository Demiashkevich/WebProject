package com.demiashkevich.movie.command;

import com.demiashkevich.movie.entity.Evaluation;
import com.demiashkevich.movie.entity.Movie;
import com.demiashkevich.movie.entity.User;
import com.demiashkevich.movie.exception.ServiceException;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.EvaluationService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class AddReviewCommand implements Command{

    private static final Logger LOGGER = Logger.getLogger(AddReviewCommand.class);

    private static final String PAGE_SUCCESS = "path.page.success";
    private static final String PAGE_ERROR = "path.page.error"; //ERROR DESCRIPTION
    private static final String PAGE_ERROR_ADD_REVIEW = "";

    private static final String ERROR_MESSAGE_VALIDATE = "VALIDATE ERROR";
    private static final String ERROR_MESSAGE_EXIST = "ADDITION ERROR";

    @Override
    public String execute(HttpServletRequest request) {
        Evaluation evaluation = new Evaluation();
        evaluation.setTitle(request.getParameter("title"));
        evaluation.setUser((User)request.getSession().getAttribute("user"));
        evaluation.setComment(request.getParameter("comment"));
        evaluation.setRating(Double.parseDouble(request.getParameter("rating")));
        Movie movie = new Movie();
        movie.setMovieId(Integer.parseInt(request.getParameter("movie_id")));
        evaluation.setMovie(movie);

        try {
            EvaluationService evaluationService = new EvaluationService();
            int result = evaluationService.addEvaluation(evaluation);
            switch (result) {
                case -1: {
                    //ERROR DESCRIPTION
                    return ConfigurationManager.getKey(PAGE_ERROR);
                }
                case 0: {
                    //ERROR DESCRIPTION
                    return ConfigurationManager.getKey(PAGE_ERROR);
                }
                default: {
                    return ConfigurationManager.getKey(PAGE_SUCCESS);
                }
            }
        } catch (ServiceException exception) {
            LOGGER.error(exception);
            request.setAttribute("error", exception);
            return ConfigurationManager.getKey(PAGE_ERROR_ADD_REVIEW);
        }
    }

}
