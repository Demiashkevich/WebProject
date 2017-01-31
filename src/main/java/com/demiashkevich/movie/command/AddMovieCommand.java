package com.demiashkevich.movie.command;

import com.demiashkevich.movie.entity.Movie;
import com.demiashkevich.movie.exception.ServiceException;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.MovieService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class AddMovieCommand extends AbstractActionMovieCommand {

    private static final Logger LOGGER = Logger.getLogger(AddMovieCommand.class);

    private static final String PAGE_SUCCESS = "path.page.success";
    private static final String PAGE_ERROR = "path.page.error";
    private static final String PAGE_ERROR_ADD_MOVIE = "path.error.page.add.movie";

    private static final String ATTR_ERROR = "error";

    @Override
    public String execute(HttpServletRequest request) {
        Movie movie = this.parseRequest(request);

        try {
            MovieService movieService = new MovieService();
            if(movieService.addMovie(movie)){
                return ConfigurationManager.getKey(PAGE_SUCCESS);
            } else {
                return ConfigurationManager.getKey(PAGE_ERROR);
            }
        } catch (ServiceException exception) {
            LOGGER.error(exception);
            request.setAttribute(ATTR_ERROR, exception);
            return ConfigurationManager.getKey(PAGE_ERROR_ADD_MOVIE);
        }
    }

}
