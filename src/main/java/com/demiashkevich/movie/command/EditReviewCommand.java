package com.demiashkevich.movie.command;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.Evaluation;
import com.demiashkevich.movie.entity.Movie;
import com.demiashkevich.movie.entity.User;
import com.demiashkevich.movie.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class EditReviewCommand implements Command {

    private static final String PAGE_ADD_REVIEW = "path.page.add_review";

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
            movie.setTitle(request.getParameter(request.getParameter("movie_title")));
            evaluation.setMovie(movie);
            evaluation.setUser((User)request.getSession().getAttribute("user"));

            request.setAttribute("evaluation", evaluation);
        } finally {
            ConnectionPool.putConnection(connection);
        }
        return ConfigurationManager.getKey(PAGE_ADD_REVIEW);
    }

}
