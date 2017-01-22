package com.demiashkevich.movie.command;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.Movie;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.memento.RequestParameter;
import com.demiashkevich.movie.memento.RequestParameterList;
import com.demiashkevich.movie.service.MovieService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ShowMovieCommand implements Command {

    private static final String PAGE_MOVIE = "path.page.movie";

    @Override
    public String execute(HttpServletRequest request) {
        ProxyConnection connection = null;
        try {
            connection = ConnectionPool.takeConnection();

            int movieId = Integer.parseInt(request.getParameter("movie_id"));
            MovieService movieService = new MovieService(connection);
            Movie movie = movieService.findMovie(movieId);

            HttpSession session = request.getSession();
            RequestParameter requestParameter = new RequestParameter();
            requestParameter.put("movie_id", String.valueOf(movieId));
            requestParameter.setCommand(EnumCommand.SHOW_MOVIE);
            RequestParameterList parameters = RequestParameterList.getInstance();
            parameters.offerLast(requestParameter);
            session.setAttribute("parameters", parameters);

            session.setAttribute("movie", movie);
        } finally {
            ConnectionPool.putConnection(connection);
        }
        return ConfigurationManager.getKey(PAGE_MOVIE);
    }

}
