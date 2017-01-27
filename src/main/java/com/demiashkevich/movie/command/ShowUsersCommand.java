package com.demiashkevich.movie.command;

import com.demiashkevich.movie.entity.User;
import com.demiashkevich.movie.exception.ServiceException;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class ShowUsersCommand implements Command{

    private static final Logger LOGGER = Logger.getLogger(ShowUsersCommand.class);

    private static final String PAGE_SHOW_USERS = "path.page.users";
    private static final String PAGE_ERROR_SHOW_USERS = "";

    @Override
    public String execute(HttpServletRequest request) {
        try {
            UserService service = new UserService();
            List<User> users = service.findUsers();
            request.setAttribute("users", users);
        } catch (ServiceException exception) {
            LOGGER.error(exception);
            request.setAttribute("error", exception);
            return ConfigurationManager.getKey(PAGE_ERROR_SHOW_USERS);
        }
        return ConfigurationManager.getKey(PAGE_SHOW_USERS);
    }

}
