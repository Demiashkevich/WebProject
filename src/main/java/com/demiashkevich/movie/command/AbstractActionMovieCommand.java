package com.demiashkevich.movie.command;

import com.demiashkevich.movie.entity.*;
import com.demiashkevich.movie.exception.ServiceException;
import com.demiashkevich.movie.service.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractActionMovieCommand implements Command {

    @Override
    public abstract String execute(HttpServletRequest request);

    void fillRequest(HttpServletRequest request) throws ServiceException {
        CategoryService categoryService = new CategoryService();
        List<Category> categories = categoryService.findAllCategories();
        request.setAttribute("categories", categories);

        CountryService countryService = new CountryService();
        List<Country> countries = countryService.findAllCountries();
        request.setAttribute("countries", countries);

        ActorService actorService = new ActorService();
        List<Actor> actors = actorService.findAllActors();
        request.setAttribute("actors", actors);

        CrewService crewService = new CrewService();
        List<Crew> crews = crewService.findAllCrews();
        request.setAttribute("crews", crews);

        RoleService roleService = new RoleService();
        List<Role> roles = roleService.findAllRoles();
        request.setAttribute("roles", roles);
    }

    Movie parseRequest(HttpServletRequest request) {
        String title = request.getParameter("title");
        String date = request.getParameter("date");
        String description = request.getParameter("description");
        String length = request.getParameter("length");
        Movie movie = new Movie();
        movie.setTitle(title);
        if(!date.isEmpty()) {
            movie.setDate(Date.valueOf(date));
        }
        movie.setDescription(description);
        if(!length.isEmpty()) {
            movie.setLength(Short.parseShort(length));
        }
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
        return movie;
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
