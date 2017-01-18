package com.demiashkevich.movie.command;

import com.demiashkevich.movie.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {

    private static final String PAGE_HOME = "path.page.home";

    @Override
    public String execute(HttpServletRequest request) {
        request.getSession(true).invalidate();
        return ConfigurationManager.getKey(PAGE_HOME);
    }

}
