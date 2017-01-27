package com.demiashkevich.movie.command;

import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.type.ClientType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {

    private static final String URL_HOME = "path.url.home";

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.removeAttribute("user");
        session.setAttribute("role", ClientType.GUEST);
        return ConfigurationManager.getKey(URL_HOME);
    }

}
