package com.demiashkevich.movie.command;

import com.demiashkevich.movie.entity.Evaluation;
import com.demiashkevich.movie.entity.Movie;
import com.demiashkevich.movie.entity.User;
import com.demiashkevich.movie.exception.ServiceException;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.manager.MessageManager;
import com.demiashkevich.movie.service.EvaluationService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class AddReviewCommand implements Command{

    private static final Logger LOGGER = Logger.getLogger(AddReviewCommand.class);

    private static final String PAR_TITLE = "title";
    private static final String ATTR_USER = "user";
    private static final String PAR_COMMENT = "comment";
    private static final String PAR_RATING = "rating";
    private static final String PAR_MOVIE_ID = "movie_id";
    private static final String ATTR_LOCALE = "locale";

    private static final String PAGE_SUCCESS = "path.page.success";
    private static final String PAGE_ERROR = "path.page.error";
    private static final String PAGE_ERROR_ADD_REVIEW = "path.error.page.add.review";

    private static final String ATTR_ERROR = "error";
    private static final String ERROR_MESSAGE_VALIDATE = "error.validation";
    private static final String ERROR_MESSAGE_EXIST = "error.review.exist";

    private static final int ERROR_VALIDATION = -1;
    private static final int ERROR_EXIST = 0;
    private static final int SUCCESS = 1;

    @Override
    public String execute(HttpServletRequest request) {
        Evaluation evaluation = new Evaluation();
        evaluation.setTitle(request.getParameter(PAR_TITLE));
        User user = (User)request.getSession().getAttribute(ATTR_USER);
        evaluation.setUser(user);
        evaluation.setComment(request.getParameter(PAR_COMMENT));
        evaluation.setRating(Double.parseDouble(request.getParameter(PAR_RATING)));
        Movie movie = new Movie();
        movie.setMovieId(Integer.parseInt(request.getParameter(PAR_MOVIE_ID)));
        evaluation.setMovie(movie);

        try {
            EvaluationService evaluationService = new EvaluationService();
            int result = evaluationService.addEvaluation(evaluation, user);
            switch (result) {
                case ERROR_VALIDATION: {
                    request.setAttribute(ATTR_ERROR, MessageManager.getKey(ERROR_MESSAGE_VALIDATE, (Locale) request.getSession().getAttribute(ATTR_LOCALE)));
                    return ConfigurationManager.getKey(PAGE_ERROR);
                }
                case ERROR_EXIST: {
                    request.setAttribute(ATTR_ERROR, MessageManager.getKey(ERROR_MESSAGE_EXIST, (Locale)request.getSession().getAttribute(ATTR_LOCALE)));
                    return ConfigurationManager.getKey(PAGE_ERROR);
                }
                case SUCCESS: {
                    return ConfigurationManager.getKey(PAGE_SUCCESS);
                }
                default: {
                    return ConfigurationManager.getKey(PAGE_ERROR);
                }
            }
        } catch (ServiceException exception) {
            LOGGER.error(exception);
            request.setAttribute(ATTR_ERROR, exception);
            return ConfigurationManager.getKey(PAGE_ERROR_ADD_REVIEW);
        }
    }

}
