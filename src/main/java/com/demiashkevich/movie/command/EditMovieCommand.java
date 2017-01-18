package com.demiashkevich.movie.command;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.*;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class EditMovieCommand implements Command {

    private static final String PAGE_EDIT_MOVIE = "path.page.edit_movie";

    @Override
    public String execute(HttpServletRequest request) {
        ProxyConnection connection = null;
        try {
            connection = ConnectionPool.takeConnection();

            CategoryService categoryService = new CategoryService(connection);
            List<Category> categories = categoryService.findAllItem();
            request.setAttribute("categories", categories);

            CountryService countryService = new CountryService(connection);
            List<Country> countries = countryService.findAllItem();
            request.setAttribute("countries", countries);

            ActorService actorService = new ActorService(connection);
            List<Actor> actors = actorService.findAllItem();
            request.setAttribute("actors", actors);

            CrewService crewService = new CrewService(connection);
            List<Crew> crews = crewService.findAllItem();
            request.setAttribute("crews", crews);

            RoleService roleService = new RoleService(connection);
            List<Role> roles = roleService.findAllItem();
            request.setAttribute("roles", roles);

        } finally {
            ConnectionPool.putConnection(connection);
        }
        return ConfigurationManager.getKey(PAGE_EDIT_MOVIE);
    }

}
