package com.demiashkevich.movie.command;

import com.demiashkevich.movie.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class AuthorizationCommand implements Command{

    private static final String PAGE_AUTHORIZATION = "path.page.authorization";

    @Override
    public String execute(HttpServletRequest request) {
        return ConfigurationManager.getKey(PAGE_AUTHORIZATION);
    }
}
