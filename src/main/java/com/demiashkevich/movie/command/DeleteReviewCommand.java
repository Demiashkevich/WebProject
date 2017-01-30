package com.demiashkevich.movie.command;

import com.demiashkevich.movie.exception.ServiceException;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.EvaluationService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class DeleteReviewCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(DeleteReviewCommand.class);

    private static final String PAGE_SUCCESS = "path.page.success";
    private static final String PAGE_ERROR_DELETE_REVIEW  = "";

    @Override
    public String execute(HttpServletRequest request) {
        try {
            long movieId = Long.parseLong(request.getParameter("movie_id"));
            int userId = Integer.parseInt(request.getParameter("user_id"));
            EvaluationService evaluationService = new EvaluationService();
            evaluationService.deleteEvaluation(userId, movieId);
        } catch (ServiceException exception) {
            LOGGER.error(exception);
            request.setAttribute("error", exception);
            return ConfigurationManager.getKey(PAGE_ERROR_DELETE_REVIEW);
        }
        return ConfigurationManager.getKey(PAGE_SUCCESS);
    }

}
