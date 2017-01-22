package com.demiashkevich.movie.command;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.User;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class SignUpCommand implements Command{

    private static final String MESSAGE = "Your account was exist.";
    private static final String PATH_REGISTRATION = "path.page.registration";
    private static final String PATH_AUTHORIZATION = "path.page.authorization";

    @Override
    public String execute(HttpServletRequest request) {
        ProxyConnection connection = null;
        try {
            connection = ConnectionPool.takeConnection();

            User user = new User();
            user.setEmail(request.getParameter("email"));
            user.setPassword(request.getParameter("password"));
            user.setFirstName(request.getParameter("first_name"));
            user.setLastName(request.getParameter("last_name"));

            UserService userService = new UserService(connection);
            if (userService.addUser(user)) {
                return ConfigurationManager.getKey(PATH_AUTHORIZATION);
            } else {
                request.setAttribute("password", user.getPassword());
                request.setAttribute("first_name", user.getFirstName());
                request.setAttribute("last_name", user.getLastName());
                request.setAttribute("message_error", MESSAGE);
                return ConfigurationManager.getKey(PATH_REGISTRATION);
            }
        } finally {
            ConnectionPool.putConnection(connection);
        }
    }

}
