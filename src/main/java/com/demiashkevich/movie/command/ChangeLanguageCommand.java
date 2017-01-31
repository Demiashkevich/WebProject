package com.demiashkevich.movie.command;

import com.demiashkevich.movie.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class ChangeLanguageCommand implements Command {

    private static final String LANGUAGE = "language";
    private static final String COUNTRY = "country";
    private static final String LOCALE = "locale";

    private static final String URL_HOME = "path.url.home";

    @Override
    public String execute(HttpServletRequest request) {
        String language = request.getParameter(LANGUAGE);
        String country = request.getParameter(COUNTRY);
        HttpSession session = request.getSession(true);
        if(language.isEmpty()){
            session.setAttribute(LOCALE, Locale.getDefault());
        } else {
            Locale locale = new Locale(language, country);
            session.setAttribute(LOCALE, locale);
        }
        return ConfigurationManager.getKey(URL_HOME);
    }

}
