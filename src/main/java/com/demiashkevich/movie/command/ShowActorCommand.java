package com.demiashkevich.movie.command;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.Actor;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.memento.RequestParameter;
import com.demiashkevich.movie.memento.RequestParameterList;
import com.demiashkevich.movie.service.ActorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ShowActorCommand implements Command{

    private static final String PAGE_ACTOR = "path.page.actor";

    @Override
    public String execute(HttpServletRequest request) {
        ProxyConnection connection = null;
        try {
            connection = ConnectionPool.takeConnection();

            int actorId = Integer.parseInt(request.getParameter("actor_id"));
            ActorService actorService = new ActorService(connection);
            Actor actor = actorService.findActor(actorId);

            HttpSession session = request.getSession();
            RequestParameter requestParameter = new RequestParameter();
            requestParameter.put("actor_id", String.valueOf(actorId));
            requestParameter.setCommand(EnumCommand.SHOW_ACTOR);
            RequestParameterList parameters = RequestParameterList.getInstance();
            parameters.offerLast(requestParameter);
            session.setAttribute("parameters", parameters);

            session.setAttribute("actor", actor);
        } finally {
            ConnectionPool.putConnection(connection);
        }
        return ConfigurationManager.getKey(PAGE_ACTOR);
    }

}
