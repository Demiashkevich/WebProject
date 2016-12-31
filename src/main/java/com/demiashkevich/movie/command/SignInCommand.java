package com.demiashkevich.movie.command;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.User;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SignInCommand implements Command {

    private static final String PAGE_SUCCESS = "path.page.success";
    private static final String PAGE_AUTHORIZATION = "path.page.authorization";
    private static final String MESSAGE = "Your email or password was entered incorrectly.";

    @Override
    public String execute(HttpServletRequest request) {
        ProxyConnection connection = null;
        try {
            connection = ConnectionPool.takeConnection();

            String email = request.getParameter("email");
            String password = request.getParameter("password");

            UserService userService = new UserService(connection);
            User user = userService.getItem(email, password);

            if (user != null) {
                HttpSession session = request.getSession(true);
                session.setAttribute("user", user);
                return ConfigurationManager.getKey(PAGE_SUCCESS);
            } else {
                request.setAttribute("message_error", MESSAGE);
                return ConfigurationManager.getKey(PAGE_AUTHORIZATION);
            }
        } finally {
            ConnectionPool.putConnection(connection);
        }
    }

}
