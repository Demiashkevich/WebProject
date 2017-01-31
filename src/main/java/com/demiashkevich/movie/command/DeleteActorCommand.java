package com.demiashkevich.movie.command;

import com.demiashkevich.movie.exception.ServiceException;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.memento.RequestParameterList;
import com.demiashkevich.movie.service.ActorService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class DeleteActorCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(DeleteActorCommand.class);

    private static final String PAR_ACTOR_ID = "actor_id";
    private static final String ATTR_PARAMETERS = "parameters";

    private static final String PAGE_SUCCESS = "path.page.success";
    private static final String PAGE_ERROR_DELETE_ACTOR = "path.error.page.delete.actor";

    private static final String ATTR_ERROR = "error";

    @Override
    public String execute(HttpServletRequest request) {
        try {
            long actorId = Long.parseLong(request.getParameter(PAR_ACTOR_ID));
            ActorService actorService = new ActorService();
            actorService.deleteActor(actorId);

            RequestParameterList parameters = (RequestParameterList)request.getSession().getAttribute(ATTR_PARAMETERS);
            parameters.pollLast();
        } catch (ServiceException exception) {
            LOGGER.error(exception);
            request.setAttribute(ATTR_ERROR, exception);
            return ConfigurationManager.getKey(PAGE_ERROR_DELETE_ACTOR);
        }
        return ConfigurationManager.getKey(PAGE_SUCCESS);
    }

}
