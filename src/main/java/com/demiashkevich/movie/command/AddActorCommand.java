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

    private static final String PAGE_SUCCESS = "path.page.success";
    private static final String PAGE_ADD_ACTOR = "path.page.add_actor";
    private static final String PAGE_ERROR = "path.page.error";
    private static final String PAGE_ERROR_ADD_ACTOR = "";


    private static final String ERROR_VALIDATE = "VALIDATE ERROR";
    private static final String ERROR_ADDITION = "ADDITION ERROR";

    @Override
    public String execute(HttpServletRequest request) {
        Actor actor = new Actor();
        actor.setFirstName(request.getParameter("first_name"));
        actor.setLastName(request.getParameter("last_name"));
        actor.setBiography(request.getParameter("biography"));
        actor.setPhoto(request.getParameter("photo"));
        String[] moviesId = request.getParameterValues("movies");
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
            request.setAttribute("error", exception);
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
