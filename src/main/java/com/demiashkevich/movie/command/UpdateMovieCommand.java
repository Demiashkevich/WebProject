package com.demiashkevich.movie.command;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.Movie;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.MovieService;

import javax.servlet.http.HttpServletRequest;

public class UpdateMovieCommand extends AbstractActionMovieCommand {

    private static final String PAGE_SUCCESS = "path.page.success";

    @Override
    public String execute(HttpServletRequest request) {
        ProxyConnection connection = null;
        try {
            connection = ConnectionPool.takeConnection();

            Movie movie = this.parseRequest(request);
            MovieService movieService = new MovieService(connection);
            if(movieService.updateItem(movie)){
                return ConfigurationManager.getKey(PAGE_SUCCESS);
            }
        } finally {
            ConnectionPool.putConnection(connection);
        }
        return null;
    }

}
