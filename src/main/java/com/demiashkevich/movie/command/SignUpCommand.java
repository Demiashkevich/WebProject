package com.demiashkevich.movie.command;

import com.demiashkevich.movie.entity.User;
import com.demiashkevich.movie.exception.ServiceException;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.manager.MessageManager;
import com.demiashkevich.movie.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class SignUpCommand implements Command{

    private static final Logger LOGGER = Logger.getLogger(SignUpCommand.class);

    private static final String ATTR_LOCALE = "locale";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";

    private static final String PAGE_ERROR = "path.page.error";
    private static final String PAGE_REGISTRATION = "path.page.registration";
    private static final String PAGE_AUTHORIZATION = "path.page.authorization";
    private static final String PAGE_ERROR_REGISTRATION = "path.error.page.registration";

    private static final String ATTR_ERROR = "error";
    private static final String ERROR_MESSAGE_EXIST = "error.registration.exist";
    private static final String ERROR_MESSAGE_VALIDATE = "error.validate";

    private static final int ERROR_VALIDATION = -1;
    private static final int ERROR_EXIST = 0;
    private static final int SUCCESS = 1;

    @Override
    public String execute(HttpServletRequest request) {
        User user = new User();
        user.setEmail(request.getParameter(EMAIL));
        user.setPassword(request.getParameter(PASSWORD));
        user.setFirstName(request.getParameter(FIRST_NAME));
        user.setLastName(request.getParameter(LAST_NAME));
        try {
            UserService userService = new UserService();
            int result = userService.addUser(user);
            switch (result) {
                case ERROR_VALIDATION: {
                    String error = MessageManager.getKey(ERROR_MESSAGE_VALIDATE, (Locale) request.getSession().getAttribute(ATTR_LOCALE));
                    request.setAttribute(ATTR_ERROR, error);
                    return ConfigurationManager.getKey(PAGE_ERROR);
                }
                case ERROR_EXIST: {
                    request.setAttribute(PASSWORD, user.getPassword());
                    request.setAttribute(FIRST_NAME, user.getFirstName());
                    request.setAttribute(LAST_NAME, user.getLastName());
                    String error = MessageManager.getKey(ERROR_MESSAGE_EXIST, (Locale) request.getSession().getAttribute(ATTR_LOCALE));
                    request.setAttribute(ATTR_ERROR, error);
                    return ConfigurationManager.getKey(PAGE_REGISTRATION);
                }
                case SUCCESS: {
                    return ConfigurationManager.getKey(PAGE_AUTHORIZATION);
                }
                default: {
                    return ConfigurationManager.getKey(PAGE_ERROR);
                }
            }
        } catch (ServiceException exception) {
            LOGGER.error(exception);
            request.setAttribute(ATTR_ERROR, exception);
            return ConfigurationManager.getKey(PAGE_ERROR_REGISTRATION);
        }
    }

}
