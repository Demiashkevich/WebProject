package com.demiashkevich.movie.command;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.Actor;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.ActorService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowActorsCommand implements Command {

    private static final int ACTOR_COUNT = 20;
    private static final String PAGE_ACTORS = "path.page.actors";

    @Override
    public String execute(HttpServletRequest request) {
        ProxyConnection connection = null;
        try {
            connection = ConnectionPool.takeConnection();

            int currentPage = Integer.parseInt(request.getParameter("currentPage"));
            ActorService actorService = new ActorService(connection);
            int countPage = actorService.countPage(ACTOR_COUNT);
            List<Actor> actors = actorService.findItems(currentPage, ACTOR_COUNT);

            request.setAttribute("actors", actors);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("countPage", countPage);
        } finally {
            ConnectionPool.putConnection(connection);
        }
        return ConfigurationManager.getKey(PAGE_ACTORS);
    }

}
