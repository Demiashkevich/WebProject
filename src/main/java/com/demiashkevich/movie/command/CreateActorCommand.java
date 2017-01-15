package com.demiashkevich.movie.command;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.Movie;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.MovieService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CreateActorCommand implements Command {

    private static final String PAGE_CREATE_ACTOR = "path.page.add_actor";

    @Override
    public String execute(HttpServletRequest request) {
        ProxyConnection connection = null;
        try {
            connection = ConnectionPool.takeConnection();

            MovieService movieService = new MovieService(connection);
            List<Movie> movies = movieService.findAllItem();
            request.setAttribute("movies", movies);

            return ConfigurationManager.getKey(PAGE_CREATE_ACTOR);
        } finally {
            ConnectionPool.putConnection(connection);
        }
    }

}
