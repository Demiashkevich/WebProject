package com.demiashkevich.movie.command;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.User;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowUsersCommand implements Command{

    private static final String PAGE_SHOW_USERS = "path.page.users";

    @Override
    public String execute(HttpServletRequest request) {
        ProxyConnection connection = null;
        try {
            connection = ConnectionPool.takeConnection();

            UserService service = new UserService(connection);
            List<User> users = service.findUsers();
            request.setAttribute("users", users);
        } finally {
            ConnectionPool.putConnection(connection);
        }
        return ConfigurationManager.getKey(PAGE_SHOW_USERS);
    }

}
