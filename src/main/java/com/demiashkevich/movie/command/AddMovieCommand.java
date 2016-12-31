package com.demiashkevich.movie.command;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.Movie;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.MovieService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

public class AddMovieCommand implements Command {

    private static final String PATH_SUCCESS = "path.page.success";
    private static final String PATH_ADD_MOVIE = "path.page.add_movie";
    private static final String ERROR_MESSAGE = "The input form <<Add Movie>> aren't valid.";

    @Override
    public String execute(HttpServletRequest request) {
        ProxyConnection connection = null;
        try {
            connection = ConnectionPool.takeConnection();
            Movie movie = new Movie();
            movie.setTitle(request.getParameter("title"));
            movie.setDate(Date.valueOf(request.getParameter("date")));
            movie.setDescription(request.getParameter("description"));
            movie.setLength(Short.parseShort(request.getParameter("length")));

            MovieService movieService = new MovieService(connection);
            if(movieService.addItem(movie)) {
                return ConfigurationManager.getKey(PATH_SUCCESS);
            } else {
                request.setAttribute("error_message", ERROR_MESSAGE);
                return ConfigurationManager.getKey(PATH_ADD_MOVIE);
            }
        } finally {
            ConnectionPool.putConnection(connection);
        }
    }

}
