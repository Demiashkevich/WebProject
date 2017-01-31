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

    private static final String CURRENT_PAGE = "currentPage";
    private static final String ACTORS = "actors";
    private static final String COUNT_PAGES = "countPage";
    private static final String ATTR_PARAMETERS = "parameters";

    private static final String ACTOR_COUNT = "actors.count.show.actor";
    private static final String PAGE_ACTORS = "path.page.actors";
    private static final String PAGE_ERROR_ACTORS = "path.page.error.transfer";

    private static final String ATTR_ERROR = "error";

    private static final int START_PAGE = 1;
    private static final int MIN_BORDER = 0;

    @Override
    public String execute(HttpServletRequest request) {
        final int COUNT = Integer.parseInt(ConfigurationManager.getKey(ACTOR_COUNT));
        int currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
        try {
            ActorService actorService = new ActorService();
            int countPage = actorService.countPage(COUNT);
            if(currentPage > countPage || currentPage <= MIN_BORDER){
                currentPage = START_PAGE;
            }
            List<Actor> actors = actorService.findActors(currentPage, COUNT);

            request.setAttribute(ACTORS, actors);
            request.setAttribute(COUNT_PAGES, countPage);
            request.setAttribute(CURRENT_PAGE, currentPage);

            HttpSession session = request.getSession();
            RequestParameter requestParameter = new RequestParameter();
            requestParameter.put(CURRENT_PAGE, String.valueOf(currentPage));
            requestParameter.setCommand(EnumCommand.SHOW_ACTORS);
            RequestParameterList parameters = (RequestParameterList)session.getAttribute(ATTR_PARAMETERS);
            parameters.offerLast(requestParameter);
            session.setAttribute(ATTR_PARAMETERS, parameters);
        } catch (ServiceException exception) {
            LOGGER.error(exception);
            request.setAttribute(ATTR_ERROR, exception);
            return ConfigurationManager.getKey(PAGE_ERROR_ACTORS);
        }
        return ConfigurationManager.getKey(PAGE_ACTORS);
    }

}
