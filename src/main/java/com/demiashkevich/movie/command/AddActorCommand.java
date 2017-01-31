package com.demiashkevich.movie.command;

import com.demiashkevich.movie.entity.Actor;
import com.demiashkevich.movie.entity.Movie;
import com.demiashkevich.movie.exception.ServiceException;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.ActorService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class AddActorCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(AddActorCommand.class);

    private static final String PAR_FIRST_NAME = "first_name";
    private static final String PAR_LAST_NAME = "last_name";
    private static final String PAR_BIOGRAPHY = "biography";
    private static final String PAR_PHOTO = "photo";
    private static final String PARS_MOVIES = "movies";

    private static final String PAGE_SUCCESS = "path.page.success";
    private static final String PAGE_ERROR = "path.page.error";
    private static final String PAGE_ERROR_ADD_ACTOR = "path.error.page.add.actor";

    private static final String ATTR_ERROR = "error";

    @Override
    public String execute(HttpServletRequest request) {
        Actor actor = new Actor();
        actor.setFirstName(request.getParameter(PAR_FIRST_NAME));
        actor.setLastName(request.getParameter(PAR_LAST_NAME));
        actor.setBiography(request.getParameter(PAR_BIOGRAPHY));
        actor.setPhoto(request.getParameter(PAR_PHOTO));
        String[] moviesId = request.getParameterValues(PARS_MOVIES);
        List<Movie> movies = this.parseMovieId(moviesId);
        actor.setMovies(movies);

        try {
            ActorService actorService = new ActorService();
            if(actorService.addActor(actor)){
                return ConfigurationManager.getKey(PAGE_SUCCESS);
            } else {
                return ConfigurationManager.getKey(PAGE_ERROR);
            }
        } catch (ServiceException exception) {
            LOGGER.error(exception);
            request.setAttribute(ATTR_ERROR, exception);
            return ConfigurationManager.getKey(PAGE_ERROR_ADD_ACTOR);
        }
    }

    private List<Movie> parseMovieId(String[] moviesId){
        List<Movie> movies = new ArrayList<>();
        for (String movieId : moviesId) {
            Movie movie = new Movie();
            movie.setMovieId(Integer.parseInt(movieId));
            movies.add(movie);
        }
        return movies;
    }

}
