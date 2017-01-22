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
            List<Actor> actors = actorService.findActors(currentPage, ACTOR_COUNT);

            request.setAttribute("actors", actors);
            request.setAttribute("countPage", countPage);
            request.setAttribute("currentPage", currentPage);

            HttpSession session = request.getSession();
            RequestParameter requestParameter = new RequestParameter();
            requestParameter.put("currentPage", String.valueOf(currentPage));
            requestParameter.setCommand(EnumCommand.SHOW_ACTORS);
            RequestParameterList parameters = RequestParameterList.getInstance();
            parameters.offerLast(requestParameter);
            session.setAttribute("parameters", parameters);
        } finally {
            ConnectionPool.putConnection(connection);
        }
        return ConfigurationManager.getKey(PAGE_ACTORS);
    }

}
