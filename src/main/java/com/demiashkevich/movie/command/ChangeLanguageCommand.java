package com.demiashkevich.movie.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangeLanguageCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String locale = request.getParameter("locale");
        HttpSession session = request.getSession();
        session.setAttribute("locale", locale);

//        Command command = ActionCommand.defineCommand(request);
        Command command = new EmptyCommand();
        return command.execute(request);
    }

}
