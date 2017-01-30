package com.demiashkevich.movie.command;

import com.demiashkevich.movie.entity.User;
import com.demiashkevich.movie.exception.ServiceException;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.UserService;
import com.demiashkevich.movie.type.ClientType;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SignInCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(SignInCommand.class);

    private static final String PAGE_SUCCESS = "path.page.success";
    private static final String PAGE_AUTHORIZATION = "path.page.authorization";
    private static final String PAGE_ERROR_AUTHORIZATION = "";
    private static final String MESSAGE = "Your email or password was entered incorrectly.";

    @Override
    public String execute(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        try {
            UserService userService = new UserService();
            User user = userService.findUser(email, password);
            if (user != null) {
                HttpSession session = request.getSession(true);
                user = userService.updateRank(user);
                if(user.isAdmin()){
                    session.setAttribute("role", ClientType.ADMINISTRATOR);
                } else {
                    session.setAttribute("role", ClientType.USER);
                }
                session.setAttribute("user", user);
                return ConfigurationManager.getKey(PAGE_SUCCESS);
            } else {
                request.setAttribute("message_error", MESSAGE);
                return ConfigurationManager.getKey(PAGE_AUTHORIZATION);
            }
        } catch (ServiceException exception) {
            LOGGER.error(exception);
            request.setAttribute("error", exception);
            return ConfigurationManager.getKey(PAGE_ERROR_AUTHORIZATION);
        }
    }

}
