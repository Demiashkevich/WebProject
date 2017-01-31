package com.demiashkevich.movie.command;

import com.demiashkevich.movie.entity.Evaluation;
import com.demiashkevich.movie.entity.Movie;
import com.demiashkevich.movie.entity.User;
import com.demiashkevich.movie.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class EditReviewCommand implements Command {

    private static final String PAR_TITLE = "title";
    private static final String PAR_COMMENT = "comment";
    private static final String PAR_RATING = "rating";
    private static final String PAR_MOVIE_ID = "movie_id";
    private static final String PAR_MOVIE_TITLE = "movie_title";
    private static final String PAR_USER_ID = "user_id";
    private static final String ATTR_EVALUATION = "evaluation";

    private static final String PAGE_ADD_REVIEW = "path.page.add_review";

    @Override
    public String execute(HttpServletRequest request) {
        Evaluation evaluation = new Evaluation();
        evaluation.setTitle(request.getParameter(PAR_TITLE));
        evaluation.setComment(request.getParameter(PAR_COMMENT));
        evaluation.setRating(Double.parseDouble(request.getParameter(PAR_RATING)));
        Movie movie = new Movie();
        movie.setMovieId(Integer.parseInt(request.getParameter(PAR_MOVIE_ID)));
        movie.setTitle(request.getParameter(request.getParameter(PAR_MOVIE_TITLE)));
        evaluation.setMovie(movie);
        User user = new User();
        user.setUserId(Integer.parseInt(request.getParameter(PAR_USER_ID)));
        evaluation.setUser(user);

        request.setAttribute(ATTR_EVALUATION, evaluation);
        return ConfigurationManager.getKey(PAGE_ADD_REVIEW);
    }

}
