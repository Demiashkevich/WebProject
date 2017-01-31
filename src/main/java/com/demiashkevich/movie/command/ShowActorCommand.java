package com.demiashkevich.movie.command;

import com.demiashkevich.movie.entity.Actor;
import com.demiashkevich.movie.exception.ServiceException;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.manager.MessageManager;
import com.demiashkevich.movie.memento.RequestParameter;
import com.demiashkevich.movie.memento.RequestParameterList;
import com.demiashkevich.movie.service.ActorService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class ShowActorCommand implements Command{

    private static final Logger LOGGER = Logger.getLogger(ShowActorCommand.class);

    private static final String PAR_ACTOR_ID = "actor_id";
    private static final String ATTR_PARAMETERS = "parameters";
    private static final String ATTR_ACTOR = "actor";
    private static final String ATTR_LOCALE = "locale";

    private static final String PAGE_ACTOR = "path.page.actor";
    private static final String PAGE_ERROR = "path.page.error";
    private static final String PAGE_ERROR_SHOW_ACTOR = "path.page.error.transfer";

    private static final String ATTR_ERROR = "error";
    private static final String ERROR_MESSAGE_EXIST = "error.actor.exist";

    @Override
    public String execute(HttpServletRequest request) {
        int actorId = Integer.parseInt(request.getParameter(PAR_ACTOR_ID));
        try {
            ActorService actorService = new ActorService();
            Actor actor = actorService.findActor(actorId);
            if(actor == null){
                String error = MessageManager.getKey(ERROR_MESSAGE_EXIST, (Locale)request.getSession().getAttribute(ATTR_LOCALE));
                request.setAttribute(ATTR_ERROR, error);
                return ConfigurationManager.getKey(PAGE_ERROR);
            } else {
                HttpSession session = request.getSession();
                RequestParameter requestParameter = new RequestParameter();
                requestParameter.put(PAR_ACTOR_ID, String.valueOf(actorId));
                requestParameter.setCommand(EnumCommand.SHOW_ACTOR);
                RequestParameterList parameters = (RequestParameterList) session.getAttribute(ATTR_PARAMETERS);
                parameters.offerLast(requestParameter);
                session.setAttribute(ATTR_PARAMETERS, parameters);

                session.setAttribute(ATTR_ACTOR, actor);
            }
        }catch (ServiceException exception) {
            LOGGER.error(exception);
            request.setAttribute(ATTR_ERROR, exception);
            return ConfigurationManager.getKey(PAGE_ERROR_SHOW_ACTOR);
        }
        return ConfigurationManager.getKey(PAGE_ACTOR);
    }

}
