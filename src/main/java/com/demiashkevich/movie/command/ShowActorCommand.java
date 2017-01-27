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

public class ShowActorCommand implements Command{

    private static final Logger LOGGER = Logger.getLogger(ShowActorCommand.class);

    private static final String PAGE_ACTOR = "path.page.actor";
    private static final String PAGE_ERROR = "path.page.error";
    private static final String PAGE_ERROR_SHOW_ACTOR = "";

    @Override
    public String execute(HttpServletRequest request) {
        int actorId = Integer.parseInt(request.getParameter("actor_id"));
        try {
            ActorService actorService = new ActorService();
            Actor actor = actorService.findActor(actorId);
            if(actor == null){
                //ERROR DESCRIPTION
                return ConfigurationManager.getKey(PAGE_ERROR);
            } else {
                HttpSession session = request.getSession();
                RequestParameter requestParameter = new RequestParameter();
                requestParameter.put("actor_id", String.valueOf(actorId));
                requestParameter.setCommand(EnumCommand.SHOW_ACTOR);
                RequestParameterList parameters = (RequestParameterList) session.getAttribute("parameters");
                parameters.offerLast(requestParameter);
                session.setAttribute("parameters", parameters);

                session.setAttribute("actor", actor);
            }
        }catch (ServiceException exception) {
            LOGGER.error(exception);
            request.setAttribute("error", exception);
            return ConfigurationManager.getKey(PAGE_ERROR_SHOW_ACTOR);
        }
        return ConfigurationManager.getKey(PAGE_ACTOR);
    }

}
