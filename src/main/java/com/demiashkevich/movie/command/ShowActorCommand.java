package com.demiashkevich.movie.command;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.Actor;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.ActorService;

import javax.servlet.http.HttpServletRequest;

public class ShowActorCommand implements Command{

    private static final String PAGE_ACTOR = "path.page.actor";

    @Override
    public String execute(HttpServletRequest request) {
        ProxyConnection connection = null;
        try {
            connection = ConnectionPool.takeConnection();

            int actorId = Integer.parseInt(request.getParameter("actor_id"));
            ActorService actorService = new ActorService(connection);
            Actor actor = actorService.findItem(actorId);
            request.setAttribute("actor", actor);
        } finally {
            ConnectionPool.putConnection(connection);
        }
        return ConfigurationManager.getKey(PAGE_ACTOR);
    }

}
