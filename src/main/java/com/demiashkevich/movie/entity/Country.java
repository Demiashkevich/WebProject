package com.demiashkevich.movie.entity;

import java.util.List;

public class Country extends Entity {

    private int countryId;
    private String name;
    private List<Movie> movies;

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Country)) return false;

        Country country = (Country) o;

        return countryId == country.countryId;

    }

    @Override
    public int hashCode() {
        return countryId;
    }
}
