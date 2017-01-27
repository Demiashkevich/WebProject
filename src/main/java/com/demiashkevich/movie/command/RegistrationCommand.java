package com.demiashkevich.movie.command;

import com.demiashkevich.movie.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements Command{

    private static final String PAGE_REGISTRATION = "path.page.registration";

    @Override
    public String execute(HttpServletRequest request) {
        return ConfigurationManager.getKey(PAGE_REGISTRATION);
    }
}
