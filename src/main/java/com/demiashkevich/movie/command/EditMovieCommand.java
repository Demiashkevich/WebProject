package com.demiashkevich.movie.command;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class EditMovieCommand extends AbstractActionMovieCommand {

    private static final String PAGE_EDIT_MOVIE = "path.page.edit_movie";

    @Override
    public String execute(HttpServletRequest request) {
        ProxyConnection connection = null;
        try {
            connection = ConnectionPool.takeConnection();

            this.fillRequest(request, connection);
        } finally {
            ConnectionPool.putConnection(connection);
        }
        return ConfigurationManager.getKey(PAGE_EDIT_MOVIE);
    }

}
