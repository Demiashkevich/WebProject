package com.demiashkevich.movie.command;

import com.demiashkevich.movie.entity.Actor;
import com.demiashkevich.movie.exception.ServiceException;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.memento.RequestParameter;
import com.demiashkevich.movie.memento.RequestParameterList;
import com.demiashkevich.movie.service.ActorService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowActorsCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(ShowActorsCommand.class);

    private static final String ACTOR_COUNT = "actors.count.show.actor";

    private static final String PAGE_ACTORS = "path.page.actors";
    private static final String PAGE_ERROR_ACTORS = "";

    private static final int START_PAGE = 1;
    private static final int MIN_BORDER = 0;

    @Override
    public String execute(HttpServletRequest request) {
        final int COUNT = Integer.parseInt(ConfigurationManager.getKey(ACTOR_COUNT));
        int currentPage = Integer.parseInt(request.getParameter("currentPage"));
        try {
            ActorService actorService = new ActorService();
            int countPage = actorService.countPage(COUNT);
            if(currentPage > countPage || currentPage <= MIN_BORDER){
                currentPage = START_PAGE;
            }
            List<Actor> actors = actorService.findActors(currentPage, COUNT);

            request.setAttribute("actors", actors);
            request.setAttribute("countPage", countPage);
            request.setAttribute("currentPage", currentPage);

            HttpSession session = request.getSession();
            RequestParameter requestParameter = new RequestParameter();
            requestParameter.put("currentPage", String.valueOf(currentPage));
            requestParameter.setCommand(EnumCommand.SHOW_ACTORS);
            RequestParameterList parameters = (RequestParameterList)session.getAttribute("parameters");
            parameters.offerLast(requestParameter);
            session.setAttribute("parameters", parameters);
        } catch (ServiceException exception) {
            LOGGER.error(exception);
            request.setAttribute("error", exception);
            return ConfigurationManager.getKey(PAGE_ERROR_ACTORS);
        }
        return ConfigurationManager.getKey(PAGE_ACTORS);
    }

}
