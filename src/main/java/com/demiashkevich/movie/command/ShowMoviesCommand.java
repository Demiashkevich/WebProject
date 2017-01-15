package com.demiashkevich.movie.command;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.Movie;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.MovieService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowMoviesCommand implements Command{

    private static final int MOVIE_COUNT = 20;
    private static final String PAGE_MOVIES = "path.page.movies";

    @Override
    public String execute(HttpServletRequest request) {
        ProxyConnection connection = null;
        try {
            connection = ConnectionPool.takeConnection();

            int currentPage = Integer.parseInt(request.getParameter("currentPage"));
            MovieService movieService = new MovieService(connection);
            int countPage = movieService.countPage(MOVIE_COUNT);
            List<Movie> movies = movieService.findItems(currentPage, MOVIE_COUNT);

            request.setAttribute("movies", movies);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("countPage", countPage);
        } finally {
            ConnectionPool.putConnection(connection);
        }
        return ConfigurationManager.getKey(PAGE_MOVIES);
    }

}
