package com.demiashkevich.movie.command;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.MovieService;

import javax.servlet.http.HttpServletRequest;

public class DeleteMovieCommand implements Command {

    private static final String PAGE_SUCCESS = "path.page.success";

    @Override
    public String execute(HttpServletRequest request) {
        ProxyConnection connection = null;
        try {
            connection = ConnectionPool.takeConnection();

            long movieId = Long.parseLong(request.getParameter("movie_id"));
            MovieService movieService = new MovieService(connection);
            if(movieService.deleteItem(movieId)) {
                return ConfigurationManager.getKey(PAGE_SUCCESS);
            }
        } finally {
            ConnectionPool.putConnection(connection);
        }
        return null;
    }

}
