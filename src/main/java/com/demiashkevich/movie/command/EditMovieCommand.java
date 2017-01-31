package com.demiashkevich.movie.command;

import com.demiashkevich.movie.exception.ServiceException;
import com.demiashkevich.movie.manager.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class EditMovieCommand extends AbstractActionMovieCommand {

    private static final Logger LOGGER = Logger.getLogger(EditMovieCommand.class);

    private static final String PAGE_EDIT_MOVIE = "path.page.edit_movie";
    private static final String PAGE_ERROR_EDIT_MOVIE = "path.error.page.transfer";

    private static final String ATTR_ERROR = "error";

    @Override
    public String execute(HttpServletRequest request) {
        try {
            this.fillRequest(request);
        }catch (ServiceException exception) {
            LOGGER.error(exception);
            request.setAttribute(ATTR_ERROR, exception);
            return ConfigurationManager.getKey(PAGE_ERROR_EDIT_MOVIE);
        }
        return ConfigurationManager.getKey(PAGE_EDIT_MOVIE);
    }

}
