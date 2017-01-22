package com.demiashkevich.movie.entity;

import java.util.List;

public class Category extends Entity {

    private int categoryId;
    private String name;
    private List<Movie> movies;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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
        if (!(o instanceof Category)) return false;

        Category category = (Category) o;

        return categoryId == category.categoryId;
    }

    @Override
    public int hashCode() {
        return categoryId;
    }
}
