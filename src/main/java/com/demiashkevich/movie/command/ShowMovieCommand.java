package com.demiashkevich.movie.command;

import com.demiashkevich.movie.entity.Movie;
import com.demiashkevich.movie.exception.ServiceException;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.memento.RequestParameter;
import com.demiashkevich.movie.memento.RequestParameterList;
import com.demiashkevich.movie.service.MovieService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ShowMovieCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(ShowMovieCommand.class);

    private static final String PAGE_MOVIE = "path.page.movie";
    private static final String PAGE_ERROR = "path.page.error"; //ERROR DESCRIPTION
    private static final String PAGE_ERROR_MOVIE = "";

    @Override
    public String execute(HttpServletRequest request) {
        int movieId = Integer.parseInt(request.getParameter("movie_id"));
        try {
            MovieService movieService = new MovieService();
            Movie movie = movieService.findMovie(movieId);
            if(movie == null){
                //ERROR DESCRIPTION
                return ConfigurationManager.getKey(PAGE_ERROR);
            } else {
                HttpSession session = request.getSession();
                RequestParameter requestParameter = new RequestParameter();
                requestParameter.put("movie_id", String.valueOf(movieId));
                requestParameter.setCommand(EnumCommand.SHOW_MOVIE);
                RequestParameterList parameters = (RequestParameterList)session.getAttribute("parameters");
                parameters.offerLast(requestParameter);
                session.setAttribute("parameters", parameters);

                session.setAttribute("movie", movie);
                return ConfigurationManager.getKey(PAGE_MOVIE);
            }
        } catch (ServiceException exception) {
            LOGGER.error(exception);
            request.setAttribute("error", exception);
            return ConfigurationManager.getKey(PAGE_ERROR_MOVIE);
        }

    }

}
