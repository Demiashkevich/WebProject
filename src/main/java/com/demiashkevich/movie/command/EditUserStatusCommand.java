package com.demiashkevich.movie.command;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class EditUserStatusCommand implements Command {

    private static final String PAGE_USERS = "path.page.users";

    @Override
    public String execute(HttpServletRequest request) {
        ProxyConnection connection = null;
        try {
            connection = ConnectionPool.takeConnection();

            int user_id = Integer.parseInt(request.getParameter("user_id"));
            boolean status = Boolean.parseBoolean(request.getParameter("status"));
            UserService service = new UserService(connection);
            service.updateStatusUser(user_id, status);
        } finally {
            ConnectionPool.putConnection(connection);
        }
        return ConfigurationManager.getKey(PAGE_USERS);
    }

}
