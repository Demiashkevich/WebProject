package com.demiashkevich.movie.command;

import com.demiashkevich.movie.entity.Movie;
import com.demiashkevich.movie.exception.ServiceException;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.memento.RequestParameter;
import com.demiashkevich.movie.memento.RequestParameterList;
import com.demiashkevich.movie.service.MovieService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowMoviesCommand implements Command{

    private static final Logger LOGGER = Logger.getLogger(ShowMoviesCommand.class);

    private static final String MOVIE_COUNT = "movies.count.show.movie";

    private static final String PAGE_MOVIES = "path.page.movies";
    private static final String PAGE_ERROR_MOVIE = "";

    private static final int START_PAGE = 1;
    private static final int MIN_BORDER = 0;

    @Override
    public String execute(HttpServletRequest request) {
        final int COUNT = Integer.parseInt(ConfigurationManager.getKey(MOVIE_COUNT));
        int currentPage = Integer.parseInt(request.getParameter("currentPage"));
        try {
            MovieService movieService = new MovieService();
            int countPage = movieService.countPage(COUNT);
            if(currentPage > countPage || currentPage <= MIN_BORDER){
                currentPage = START_PAGE;
            }
            List<Movie> movies = movieService.findMovies(currentPage, COUNT);

            request.setAttribute("movies", movies);
            request.setAttribute("countPage", countPage);
            request.setAttribute("currentPage", currentPage);

            HttpSession session = request.getSession();
            RequestParameter requestParameter = new RequestParameter();
            requestParameter.put("currentPage", String.valueOf(currentPage));
            requestParameter.setCommand(EnumCommand.SHOW_MOVIES);
            RequestParameterList parameters = (RequestParameterList)session.getAttribute("parameters");
            parameters.offerLast(requestParameter);
            session.setAttribute("parameter", parameters);
        } catch (ServiceException exception) {
            LOGGER.error(exception);
            request.setAttribute("error", exception);
            return ConfigurationManager.getKey(PAGE_ERROR_MOVIE);
        }
        return ConfigurationManager.getKey(PAGE_MOVIES);
    }

}
