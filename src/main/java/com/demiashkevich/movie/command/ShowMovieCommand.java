package com.demiashkevich.movie.command;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.Movie;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.MovieService;

import javax.servlet.http.HttpServletRequest;

public class ShowMovieCommand implements Command {

    private static final String PAGE_MOVIE = "path.page.movie";

    @Override
    public String execute(HttpServletRequest request) {
        ProxyConnection connection = null;
        try {
            connection = ConnectionPool.takeConnection();

            int movieId = Integer.parseInt(request.getParameter("movie_id"));
            MovieService movieService = new MovieService(connection);
            Movie movie = movieService.findItem(movieId);
            request.setAttribute("movie", movie);
        } finally {
            ConnectionPool.putConnection(connection);
        }
        return ConfigurationManager.getKey(PAGE_MOVIE);
    }

}
