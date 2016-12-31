package com.demiashkevich.movie.command;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.Actor;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.ActorService;

import javax.servlet.http.HttpServletRequest;

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

            ActorService actorService = new ActorService(connection);
            if(actorService.addItem(actor)) {
                return ConfigurationManager.getKey(PATH_SUCCESS);
            } else {
                request.setAttribute("error_message", ERROR_MESSAGE);
                return ConfigurationManager.getKey(PATH_ADD_ACTOR);
            }
        } finally {
            ConnectionPool.putConnection(connection);
        }
    }

}
