package com.demiashkevich.movie.command;

import com.demiashkevich.movie.entity.Evaluation;
import com.demiashkevich.movie.entity.Movie;
import com.demiashkevich.movie.entity.User;
import com.demiashkevich.movie.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class EditReviewCommand implements Command {

    private static final String PAGE_ADD_REVIEW = "path.page.add_review";

    @Override
    public String execute(HttpServletRequest request) {
        Evaluation evaluation = new Evaluation();
        evaluation.setTitle(request.getParameter("title"));
        evaluation.setComment(request.getParameter("comment"));
        evaluation.setRating(Double.parseDouble(request.getParameter("rating")));
        Movie movie = new Movie();
        movie.setMovieId(Integer.parseInt(request.getParameter("movie_id")));
        movie.setTitle(request.getParameter(request.getParameter("movie_title")));
        evaluation.setMovie(movie);
        User user = new User();
        user.setUserId(Integer.parseInt(request.getParameter("user_id")));
        evaluation.setUser(user);

        request.setAttribute("evaluation", evaluation);
        return ConfigurationManager.getKey(PAGE_ADD_REVIEW);
    }

}
