package com.demiashkevich.movie.command;

import com.demiashkevich.movie.entity.User;
import com.demiashkevich.movie.exception.ServiceException;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.manager.MessageManager;
import com.demiashkevich.movie.service.UserService;
import com.demiashkevich.movie.type.ClientType;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class SignInCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(SignInCommand.class);

    private static final String PAR_EMAIL = "email";
    private static final String PAR_PASSWORD = "password";
    private static final String ATTR_ROLE = "role";
    private static final String ATTR_USER = "user";
    private static final String ATTR_LOCALE = "locale";

    private static final String PAGE_SUCCESS = "path.page.success";
    private static final String PAGE_AUTHORIZATION = "path.page.authorization";
    private static final String PAGE_ERROR_AUTHORIZATION = "path.error.page.authorization";

    private static final String ATTR_ERROR = "error";
    private static final String ERROR_MESSAGE_INPUT = "error.authorization";

    @Override
    public String execute(HttpServletRequest request) {
        String email = request.getParameter(PAR_EMAIL);
        String password = request.getParameter(PAR_PASSWORD);
        try {
            UserService userService = new UserService();
            User user = userService.findUser(email, password);
            if (user != null) {
                HttpSession session = request.getSession(true);
                user = userService.updateRank(user);
                if(user.isAdmin()){
                    session.setAttribute(ATTR_ROLE, ClientType.ADMINISTRATOR);
                } else {
                    session.setAttribute(ATTR_ROLE, ClientType.USER);
                }
                session.setAttribute(ATTR_USER, user);
                return ConfigurationManager.getKey(PAGE_SUCCESS);
            } else {
                String error = MessageManager.getKey(ERROR_MESSAGE_INPUT, (Locale)request.getSession().getAttribute(ATTR_LOCALE));
                request.setAttribute(ATTR_ERROR, error);
                return ConfigurationManager.getKey(PAGE_AUTHORIZATION);
            }
        } catch (ServiceException exception) {
            LOGGER.error(exception);
            request.setAttribute(ATTR_ERROR, exception);
            return ConfigurationManager.getKey(PAGE_ERROR_AUTHORIZATION);
        }
    }

}
