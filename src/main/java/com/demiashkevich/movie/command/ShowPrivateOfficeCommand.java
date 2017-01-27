package com.demiashkevich.movie.command;

import com.demiashkevich.movie.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class ShowPrivateOfficeCommand implements Command{

    private static final String PAGE_PRIVATE_OFFICE = "path.page.private_office";

    @Override
    public String execute(HttpServletRequest request) {
        return ConfigurationManager.getKey(PAGE_PRIVATE_OFFICE);
    }

}
