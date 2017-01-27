package com.demiashkevich.movie.command;

import com.demiashkevich.movie.entity.User;
import com.demiashkevich.movie.exception.ServiceException;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class SignUpCommand implements Command{

    private static final Logger LOGGER = Logger.getLogger(SignUpCommand.class);

    private static final String MESSAGE = "This email was exist.";
    private static final String PAGE_ERROR = "path.page.error";
    private static final String PAGE_REGISTRATION = "path.page.registration";
    private static final String PAGE_AUTHORIZATION = "path.page.authorization";
    private static final String PAGE_ERROR_REGISTRATION = "";

    private static final String ERROR_VALIDATE = "VALIDATE ERROR";
    private static final String ERROR_ADDITION = "ADDITION ERROR";

    @Override
    public String execute(HttpServletRequest request) {
        User user = new User();
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));
        user.setFirstName(request.getParameter("first_name"));
        user.setLastName(request.getParameter("last_name"));
        try {
            UserService userService = new UserService();
            int result = userService.addUser(user);
            switch (result) {
                case -1: {
                    request.setAttribute("error", ERROR_VALIDATE);
                    return ConfigurationManager.getKey(PAGE_ERROR);
                }
                case 0: {
                    request.setAttribute("password", user.getPassword());
                    request.setAttribute("first_name", user.getFirstName());
                    request.setAttribute("last_name", user.getLastName());
                    request.setAttribute("message_error", MESSAGE);
                    return ConfigurationManager.getKey(PAGE_REGISTRATION);
                }
                default: {
                    return ConfigurationManager.getKey(PAGE_AUTHORIZATION);
                }
            }
        } catch (ServiceException exception) {
            LOGGER.error(exception);
            request.setAttribute("error", exception);
            return ConfigurationManager.getKey(PAGE_ERROR_REGISTRATION);
        }
    }

}
