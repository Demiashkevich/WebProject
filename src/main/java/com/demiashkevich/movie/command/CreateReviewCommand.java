package com.demiashkevich.movie.command;

import com.demiashkevich.movie.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class CreateReviewCommand implements Command{

    private static final String PAGE_CREATE_REVIEW = "path.page.add_review";

    @Override
    public String execute(HttpServletRequest request) {
        return ConfigurationManager.getKey(PAGE_CREATE_REVIEW);
    }

}
