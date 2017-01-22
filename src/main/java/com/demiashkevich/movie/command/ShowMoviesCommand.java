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
import java.util.List;

public class ShowMoviesCommand implements Command{

    private static final int MOVIE_COUNT = 4;
    private static final String PAGE_MOVIES = "path.page.movies";

    @Override
    public String execute(HttpServletRequest request) {
        ProxyConnection connection = null;
        try {
            connection = ConnectionPool.takeConnection();

            int currentPage = Integer.parseInt(request.getParameter("currentPage"));
            MovieService movieService = new MovieService(connection);
            int countPage = movieService.countPage(MOVIE_COUNT);
            List<Movie> movies = movieService.findMovies(currentPage, MOVIE_COUNT);

            request.setAttribute("movies", movies);
            request.setAttribute("countPage", countPage);
            request.setAttribute("currentPage", currentPage);

            HttpSession session = request.getSession();
            RequestParameter requestParameter = new RequestParameter();
            requestParameter.put("currentPage", String.valueOf(currentPage));
            requestParameter.setCommand(EnumCommand.SHOW_MOVIES);
            RequestParameterList parameters = RequestParameterList.getInstance();
            parameters.offerLast(requestParameter);
            session.setAttribute("parameter", parameters);
        } finally {
            ConnectionPool.putConnection(connection);
        }
        return ConfigurationManager.getKey(PAGE_MOVIES);
    }

}
