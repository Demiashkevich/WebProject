package com.demiashkevich.movie.command;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.ActorService;

import javax.servlet.http.HttpServletRequest;

public class DeleteActorCommand implements Command {

    private static final String PAGE_SUCCESS = "path.page.success";
    private static final String PAGE_ERROR = "path.page.error";

    @Override
    public String execute(HttpServletRequest request) {
        ProxyConnection connection = null;
        try {
            connection = ConnectionPool.takeConnection();

            long actorId = Long.parseLong(request.getParameter("actor_id"));
            ActorService actorService = new ActorService(connection);
            if(actorService.deleteActor(actorId)) {
                return ConfigurationManager.getKey(PAGE_SUCCESS);
            } else {
                return ConfigurationManager.getKey(PAGE_ERROR);
            }
        } finally {
            ConnectionPool.putConnection(connection);
        }
    }

}
