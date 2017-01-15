package com.demiashkevich.movie.command;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.Actor;
import com.demiashkevich.movie.entity.Movie;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.ActorService;
import com.demiashkevich.movie.service.MovieService;

import javax.servlet.http.HttpServletRequest;
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
            List<Movie> movies = movieService.findItems(COUNT_MOVIES);
            request.setAttribute("movies", movies);

            ActorService actorService = new ActorService(connection);
            List<Actor> actors = actorService.findItems(COUNT_ACTORS);
            request.setAttribute("actors", actors);

            request.setAttribute("locale", Locale.getDefault());
        } finally {
            ConnectionPool.putConnection(connection);
        }
        return ConfigurationManager.getKey(PAGE_HOME);
    }
}
