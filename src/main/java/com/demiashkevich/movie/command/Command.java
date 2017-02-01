package com.demiashkevich.movie.command;

import javax.servlet.http.HttpServletRequest;

public interface Command {

    /**Command
     * @param request - HttpServletRequest
     * @return String page path
     */
    public String execute(HttpServletRequest request);

}
