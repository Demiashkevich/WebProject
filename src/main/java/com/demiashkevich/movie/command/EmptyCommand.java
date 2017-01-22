package com.demiashkevich.movie.command;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.Actor;
import com.demiashkevich.movie.entity.Movie;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.memento.RequestParameter;
import com.demiashkevich.movie.memento.RequestParameterList;
import com.demiashkevich.movie.service.ActorService;
import com.demiashkevich.movie.service.MovieService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;

public class EmptyCommand implements Command {

    private static final String PAGE_HOME = "path.page.home";
    private static final int COUNT_MOVIES = 8;
    private static final int COUNT_ACTORS = 4;

    @Override
    public String execute(HttpServletRequest request) {
        ProxyConnection connection = null;
            try {
                connection = ConnectionPool.takeConnection();

                MovieService movieService = new MovieService(connection);
                List<Movie> movies = movieService.findMovies(COUNT_MOVIES);
                request.setAttribute("movies", movies);

                ActorService actorService = new ActorService(connection);
                List<Actor> actors = actorService.findActors(COUNT_ACTORS);
                request.setAttribute("actors", actors);

                RequestParameterList parameters = RequestParameterList.getInstance();
                HttpSession session = request.getSession(true);
                RequestParameter parameter = new RequestParameter();
                parameter.setCommand(EnumCommand.EMPTY);
                parameters.offerLast(parameter);
                session.setAttribute("parameters", parameters);

                request.setAttribute("locale", Locale.getDefault());
            } finally {
                ConnectionPool.putConnection(connection);
            }
        return ConfigurationManager.getKey(PAGE_HOME);
    }
}
