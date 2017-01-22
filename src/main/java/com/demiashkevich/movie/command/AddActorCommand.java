package com.demiashkevich.movie.command;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.Actor;
import com.demiashkevich.movie.entity.Movie;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.ActorService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class AddActorCommand implements Command {

    private static final String PATH_SUCCESS = "path.page.success";
    private static final String PATH_ADD_ACTOR = "path.page.add_actor";
    private static final String ERROR_MESSAGE = "The input form <<Add Actor>> aren't valid.";

    @Override
    public String execute(HttpServletRequest request) {
        ProxyConnection connection = null;
        try {
            connection = ConnectionPool.takeConnection();

            Actor actor = new Actor();
            actor.setFirstName(request.getParameter("first_name"));
            actor.setLastName(request.getParameter("last_name"));
            actor.setBiography(request.getParameter("biography"));
            actor.setPhoto(request.getParameter("photo"));
            String[] moviesId = request.getParameterValues("movies");
            List<Movie> movies = this.parseMovieId(moviesId);
            actor.setMovies(movies);

            ActorService actorService = new ActorService(connection);
            if(actorService.addActor(actor)) {
                return ConfigurationManager.getKey(PATH_SUCCESS);
            } else {
                request.setAttribute("error_message", ERROR_MESSAGE);
                return ConfigurationManager.getKey(PATH_ADD_ACTOR);
            }
        } finally {
            ConnectionPool.putConnection(connection);
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
