package com.demiashkevich.movie.command;

import com.demiashkevich.movie.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {

    private static final String PAGE_SUCCESS = "path.page.success";

    @Override
    public String execute(HttpServletRequest request) {
        request.getSession(true).removeAttribute("user");
        return ConfigurationManager.getKey(PAGE_SUCCESS);
    }

}
