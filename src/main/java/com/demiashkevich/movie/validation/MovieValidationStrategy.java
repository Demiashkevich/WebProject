package com.demiashkevich.movie.validation;

import com.demiashkevich.movie.entity.Movie;

import static com.demiashkevich.movie.validation.ItemValidation.*;

public class MovieValidationStrategy implements ValidationStrategy<Movie> {

    @Override
    public boolean validate(Movie movie) {
        return dateValidation(String.valueOf(movie.getDate())) && lengthValidation(String.valueOf(movie.getLength()));
    }

}
