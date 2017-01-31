package com.demiashkevich.movie.command;

import com.demiashkevich.movie.entity.Movie;
import com.demiashkevich.movie.exception.ServiceException;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.manager.MessageManager;
import com.demiashkevich.movie.memento.RequestParameter;
import com.demiashkevich.movie.memento.RequestParameterList;
import com.demiashkevich.movie.service.MovieService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class ShowMovieCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(ShowMovieCommand.class);

    private static final String PAR_MOVIE_ID = "movie_id";
    private static final String PARAMETERS = "parameters";
    private static final String ATTR_MOVIE = "movie";
    private static final String ATTR_LOCALE = "locale";

    private static final String PAGE_MOVIE = "path.page.movie";
    private static final String PAGE_ERROR = "path.page.error";
    private static final String PAGE_ERROR_MOVIE = "path.page.error.transfer";

    private static final String ATTR_ERROR = "error";
    private static final String ERROR_MESSAGE_EXIST = "error.movie.exist";

    @Override
    public String execute(HttpServletRequest request) {
        int movieId = Integer.parseInt(request.getParameter(PAR_MOVIE_ID));
        try {
            MovieService movieService = new MovieService();
            Movie movie = movieService.findMovie(movieId);
            if(movie == null){
                String error = MessageManager.getKey(ERROR_MESSAGE_EXIST, (Locale) request.getSession().getAttribute(ATTR_LOCALE));
                request.setAttribute(ATTR_ERROR, error);
                return ConfigurationManager.getKey(PAGE_ERROR);
            } else {
                HttpSession session = request.getSession();
                RequestParameter requestParameter = new RequestParameter();
                requestParameter.put(PAR_MOVIE_ID, String.valueOf(movieId));
                requestParameter.setCommand(EnumCommand.SHOW_MOVIE);
                RequestParameterList parameters = (RequestParameterList)session.getAttribute(PARAMETERS);
                parameters.offerLast(requestParameter);
                session.setAttribute(PARAMETERS, parameters);

                session.setAttribute(ATTR_MOVIE, movie);
                return ConfigurationManager.getKey(PAGE_MOVIE);
            }
        } catch (ServiceException exception) {
            LOGGER.error(exception);
            request.setAttribute(ATTR_ERROR, exception);
            return ConfigurationManager.getKey(PAGE_ERROR_MOVIE);
        }

    }

}
