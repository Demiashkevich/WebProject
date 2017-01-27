package com.demiashkevich.movie.command;

import com.demiashkevich.movie.entity.Actor;
import com.demiashkevich.movie.entity.Movie;
import com.demiashkevich.movie.exception.ServiceException;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.memento.RequestParameter;
import com.demiashkevich.movie.memento.RequestParameterList;
import com.demiashkevich.movie.service.ActorService;
import com.demiashkevich.movie.service.MovieService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class EmptyCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(EmptyCommand.class);

    private static final String COUNT_MOVIES = "home.count.show.movie";
    private static final String COUNT_ACTORS = "home.count.show.actor";

    private static final String PAGE_HOME = "path.page.home";
    private static final String PAGE_ERROR_HOME = "";

    @Override
    public String execute(HttpServletRequest request) {
        final int COUNT_M = Integer.parseInt(ConfigurationManager.getKey(COUNT_MOVIES));
        final int COUNT_A = Integer.parseInt(ConfigurationManager.getKey(COUNT_ACTORS));
        try {
            MovieService movieService = new MovieService();
            List<Movie> movies = movieService.findMovies(COUNT_M);

            request.setAttribute("movies", movies);

            ActorService actorService = new ActorService();
            List<Actor> actors = actorService.findActors(COUNT_A);
            request.setAttribute("actors", actors);

            HttpSession session = request.getSession(true);
            RequestParameter parameter = new RequestParameter();
            parameter.setCommand(EnumCommand.EMPTY);
            RequestParameterList parameters = (RequestParameterList)session.getAttribute("parameters");
            parameters.offerLast(parameter);
            session.setAttribute("parameters", parameters);
        }catch (ServiceException exception) {
            LOGGER.error(exception);
            request.setAttribute("error", exception);
            return ConfigurationManager.getKey(PAGE_ERROR_HOME);
        }
        return ConfigurationManager.getKey(PAGE_HOME);
    }
}
