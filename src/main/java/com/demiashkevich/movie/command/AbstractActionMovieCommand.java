package com.demiashkevich.movie.command;

import com.demiashkevich.movie.entity.*;
import com.demiashkevich.movie.exception.ServiceException;
import com.demiashkevich.movie.service.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractActionMovieCommand implements Command {

    private static final String CATEGORIES = "categories";
    private static final String COUNTRIES = "countries";
    private static final String ACTORS = "actors";
    private static final String CREWS = "crews";
    private static final String ROLES = "roles";

    private static final String PAR_TITLE = "title";
    private static final String PAR_DATE = "date";
    private static final String PAR_DESCRIPTION = "description";
    private static final String PAR_LENGTH = "length";

    @Override
    public abstract String execute(HttpServletRequest request);


    /**Fill request
     * @param request
     * @throws ServiceException
     */
    void fillRequest(HttpServletRequest request) throws ServiceException {
        CategoryService categoryService = new CategoryService();
        List<Category> categories = categoryService.findAllCategories();
        request.setAttribute(CATEGORIES, categories);

        CountryService countryService = new CountryService();
        List<Country> countries = countryService.findAllCountries();
        request.setAttribute(COUNTRIES, countries);

        ActorService actorService = new ActorService();
        List<Actor> actors = actorService.findAllActors();
        request.setAttribute(ACTORS, actors);

        CrewService crewService = new CrewService();
        List<Crew> crews = crewService.findAllCrews();
        request.setAttribute(CREWS, crews);

        RoleService roleService = new RoleService();
        List<Role> roles = roleService.findAllRoles();
        request.setAttribute(ROLES, roles);
    }

    /**Parse request, create and fill object Movie
     * @param request
     * @return object movie
     */
    Movie parseRequest(HttpServletRequest request) {
        String title = request.getParameter(PAR_TITLE);
        String date = request.getParameter(PAR_DATE);
        String description = request.getParameter(PAR_DESCRIPTION);
        String length = request.getParameter(PAR_LENGTH);
        Movie movie = new Movie();
        movie.setTitle(title);
        if(!date.isEmpty()) {
            movie.setDate(Date.valueOf(date));
        }
        movie.setDescription(description);
        if(!length.isEmpty()) {
            movie.setLength(Short.parseShort(length));
        }
        String[] categoriesId = request.getParameterValues(CATEGORIES);
        List<Category> categories = this.parseCategoriesId(categoriesId);
        movie.setCategories(categories);
        String[] countriesId = request.getParameterValues(COUNTRIES);
        List<Country> countries = this.parseCountriesId(countriesId);
        movie.setCountries(countries);
        String[] crewsId = request.getParameterValues(CREWS);
        String[] rolesId = request.getParameterValues(ROLES);
        List<Crew> crews = this.parseCrewsRolesId(crewsId, rolesId);
        movie.setCrews(crews);
        String[] actorsId = request.getParameterValues(ACTORS);
        List<Actor> actors = this.parseActorId(actorsId);
        movie.setActors(actors);
        return movie;
    }


    /**Create list objects with parameters
     * @param categoriesId
     * @return list categories with field categoryId
     */
    private List<Category> parseCategoriesId(String[] categoriesId){
        List<Category> categories = new ArrayList<>();
        for(String categoryId : categoriesId){
            Category category = new Category();
            category.setCategoryId(Integer.parseInt(categoryId));
            categories.add(category);
        }
        return categories;
    }


    /**Create list objects with parameters
     * @param countriesId
     * @return list counties with filed countryId
     */
    private List<Country> parseCountriesId(String[] countriesId){
        List<Country> categories = new ArrayList<>();
        for(String countryId : countriesId){
            Country country = new Country();
            country.setCountryId(Integer.parseInt(countryId));
            categories.add(country);
        }
        return categories;
    }


    /**Create list objects with parameters
     * @param crewsId
     * @param rolesId
     * @return list crew with field crewId
     */
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

    /**Create list objects with parameters
     * @param actorsId
     * @return list actors with field actorId
     */
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
