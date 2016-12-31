package com.demiashkevich.movie.command;

import com.demiashkevich.movie.entity.Movie;

import javax.servlet.http.HttpServletRequest;

public class ShowMovieCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        int movieId = (Movie)request.getParameter("movie_id");

        return null;
    }

}
