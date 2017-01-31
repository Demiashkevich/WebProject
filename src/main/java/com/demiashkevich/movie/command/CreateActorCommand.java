package com.demiashkevich.movie.command;

import com.demiashkevich.movie.entity.Movie;
import com.demiashkevich.movie.exception.ServiceException;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.MovieService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CreateActorCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(CreateActorCommand.class);

    private static final String ATTR_MOVIES = "movies";

    private static final String PAGE_CREATE_ACTOR = "path.page.add_actor";
    private static final String PAGE_ERROR_CREATE_ACTOR = "path.error.page.transfer";

    private static final String ATTR_ERROR = "error";
    @Override
    public String execute(HttpServletRequest request) {
        try {
            MovieService movieService = new MovieService();
            List<Movie> movies = movieService.findAllMovies();
            request.setAttribute(ATTR_MOVIES, movies);
        } catch (ServiceException exception) {
            LOGGER.error(exception);
            request.setAttribute(ATTR_ERROR, exception);
            return ConfigurationManager.getKey(PAGE_ERROR_CREATE_ACTOR);
        }
        return ConfigurationManager.getKey(PAGE_CREATE_ACTOR);
    }

}
