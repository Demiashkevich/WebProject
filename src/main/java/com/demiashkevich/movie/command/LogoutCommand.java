package com.demiashkevich.movie.command;

import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.type.ClientType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {

    private static final String ATTR_USER = "user";
    private static final String ATTR_ROLE = "role";

    private static final String URL_HOME = "path.url.home";

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.removeAttribute(ATTR_USER);
        session.setAttribute(ATTR_ROLE, ClientType.GUEST);
        return ConfigurationManager.getKey(URL_HOME);
    }

}
