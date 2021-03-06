package com.demiashkevich.movie.command;

import com.demiashkevich.movie.entity.User;
import com.demiashkevich.movie.exception.ServiceException;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class EditUserStatusCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(EditUserStatusCommand.class);

    private static final String PAR_USER_ID = "user_id";
    private static final String PAR_STATUS = "status";
    private static final String ATTR_USERS = "users";

    private static final String PAGE_USERS = "path.page.users";
    private static final String PAGE_ERROR_EDIT_USER_STATUS = "path.error.page.update.status";

    private static final String ATTR_ERROR = "error";

    @Override
    public String execute(HttpServletRequest request) {
        int user_id = Integer.parseInt(request.getParameter(PAR_USER_ID));
        boolean status = Boolean.parseBoolean(request.getParameter(PAR_STATUS));
        try {
            UserService service = new UserService();
            service.updateStatusUser(user_id, status);
            List<User> users = service.findUsers();
            request.setAttribute(ATTR_USERS, users);
        }catch (ServiceException exception) {
            LOGGER.error(exception);
            request.setAttribute(ATTR_ERROR, exception);
            return ConfigurationManager.getKey(PAGE_ERROR_EDIT_USER_STATUS);
        }
        return ConfigurationManager.getKey(PAGE_USERS);
    }

}
