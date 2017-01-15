package com.demiashkevich.movie.command;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.EvaluationService;

import javax.servlet.http.HttpServletRequest;

public class DeleteReviewCommand implements Command {

    private static final String PAGE_SUCCESS = "path.page.success";

    @Override
    public String execute(HttpServletRequest request) {
        ProxyConnection connection = null;
        try {
            connection = ConnectionPool.takeConnection();

            long movieId = Long.parseLong(request.getParameter("movie_id"));
            long userId = Long.parseLong(request.getParameter("user_id"));
            EvaluationService evaluationService = new EvaluationService(connection);
            if(evaluationService.deleteItem(userId, movieId)) {
                return ConfigurationManager.getKey(PAGE_SUCCESS);
            }
        } finally {
            ConnectionPool.putConnection(connection);
        }
        return null;
    }

}
