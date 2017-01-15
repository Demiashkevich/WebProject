package com.demiashkevich.movie.command;

import com.demiashkevich.movie.connection.ConnectionPool;
import com.demiashkevich.movie.connection.ProxyConnection;
import com.demiashkevich.movie.entity.*;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.service.MovieService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class AddMovieCommand implements Command {

    private static final String PATH_SUCCESS = "path.page.success";
    private static final String PATH_ADD_MOVIE = "path.page.add_movie";
    private static final String ERROR_MESSAGE = "The input form <<Add Movie>> aren't valid.";

    @Override
    public String execute(HttpServletRequest request) {
        ProxyConnection connection = null;
        try {
            connection = ConnectionPool.takeConnection();

            Movie movie = new Movie();
            movie.setTitle(request.getParameter("title"));
            movie.setDate(Date.valueOf(request.getParameter("date")));
            movie.setDescription(request.getParameter("description"));
            movie.setLength(Short.parseShort(request.getParameter("length")));
            String[] categoriesId = request.getParameterValues("categories");
            List<Category> categories = this.parseCategoriesId(categoriesId);
            movie.setCategories(categories);
            String[] countriesId = request.getParameterValues("countries");
            List<Country> countries = this.parseCountriesId(countriesId);
            movie.setCountries(countries);
            String[] crewsId = request.getParameterValues("crews");
            String[] rolesId = request.getParameterValues("roles");
            List<Crew> crews = this.parseCrewsRolesId(crewsId, rolesId);
            movie.setCrews(crews);
            String[] actorsId = request.getParameterValues("actors");
            List<Actor> actors = this.parseActorId(actorsId);
            movie.setActors(actors);

            MovieService movieService = new MovieService(connection);
            if(movieService.addItem(movie)) {
                return ConfigurationManager.getKey(PATH_SUCCESS);
            } else {
                request.setAttribute("error_message", ERROR_MESSAGE);
                return ConfigurationManager.getKey(PATH_ADD_MOVIE);
            }
        } finally {
            ConnectionPool.putConnection(connection);
        }
    }

    private List<Category> parseCategoriesId(String[] categoriesId){
        List<Category> categories = new ArrayList<>();
        for(String categoryId : categoriesId){
            Category category = new Category();
            category.setCategoryId(Integer.parseInt(categoryId));
            categories.add(category);
        }
        return categories;
    }

    private List<Country> parseCountriesId(String[] countriesId){
        List<Country> categories = new ArrayList<>();
        for(String countryId : countriesId){
            Country country = new Country();
            country.setCountryId(Integer.parseInt(countryId));
            categories.add(country);
        }
        return categories;
    }

    private List<Crew> parseCrewsRolesId(String[] crewsId, String[] rolesId){
        List<Crew> crews = new ArrayList<>();
        if(crewsId.length == rolesId.length) {
            for (int i = 0; i < crewsId.length; i++) {
                Crew crew = new Crew();
                Role role = new Role();
                role.setRoleId(Integer.parseInt(rolesId[i]));
                crew.setCrewId(Integer.parseInt(crewsId[i]));
                crew.setRole(role);
                crews.add(crew);
            }
        }
        return crews;
    }

    private List<Actor> parseActorId(String[] actorsId){
        List<Actor> actors = new ArrayList<>();
        for(String actorId : actorsId){
            Actor actor = new Actor();
            actor.setActorId(Integer.parseInt(actorId));
            actors.add(actor);
        }
        return actors;
    }

}
