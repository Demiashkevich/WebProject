package com.demiashkevich.movie.command;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.ActorService;

import javax.servlet.http.HttpServletRequest;

public class DeleteActorCommand implements Command {

    private static final String PAGE_SUCCESS = "path.page.success";

    @Override
    public String execute(HttpServletRequest request) {
        ProxyConnection connection = null;
        try {
            connection = ConnectionPool.takeConnection();

            long actor_id = Long.parseLong(request.getParameter("actor_id"));
            ActorService actorService = new ActorService(connection);
            if(actorService.deleteItem(actor_id)) {
                return ConfigurationManager.getKey(PAGE_SUCCESS);
            }
        } finally {
            ConnectionPool.putConnection(connection);
        }
        return null;
    }

}
