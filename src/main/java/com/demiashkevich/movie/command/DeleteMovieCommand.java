package com.demiashkevich.movie.command;

import com.demiashkevich.movie.exception.ServiceException;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.MovieService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class DeleteMovieCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(DeleteMovieCommand.class);

    private static final String PAGE_SUCCESS = "path.page.success";
    private static final String PAGE_ERROR_DELETE_ACTOR = "";

    @Override
    public String execute(HttpServletRequest request) {
        try {
            long movieId = Long.parseLong(request.getParameter("movie_id"));
            MovieService movieService = new MovieService();
            movieService.deleteMovie(movieId);
        } catch (ServiceException exception) {
            LOGGER.error(exception);
            request.setAttribute("error", exception);
            return ConfigurationManager.getKey(PAGE_ERROR_DELETE_ACTOR);
        }
        return ConfigurationManager.getKey(PAGE_SUCCESS);
    }

}
