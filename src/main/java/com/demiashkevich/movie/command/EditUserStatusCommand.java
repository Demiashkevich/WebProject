package com.demiashkevich.movie.command;

import com.demiashkevich.movie.exception.ServiceException;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class EditUserStatusCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(EditUserStatusCommand.class);

    private static final String PAGE_USERS = "path.page.users";
    private static final String PAGE_ERROR_EDIT_USER_STATUS = "";

    @Override
    public String execute(HttpServletRequest request) {
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        boolean status = Boolean.parseBoolean(request.getParameter("status"));
        try {
            UserService service = new UserService();
            service.updateStatusUser(user_id, status);
        }catch (ServiceException exception) {
            LOGGER.error(exception);
            request.setAttribute("error", exception);
            return ConfigurationManager.getKey(PAGE_ERROR_EDIT_USER_STATUS);
        }
        return ConfigurationManager.getKey(PAGE_USERS);
    }

}
