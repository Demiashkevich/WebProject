package com.demiashkevich.movie.validation;

import com.demiashkevich.movie.entity.Movie;

import java.sql.Date;

public class MovieValidation implements Validation<Movie> {

    @Override
    public boolean execute(Movie movie) {
        String title = movie.getTitle();
        String description = movie.getDescription();
        Date date = movie.getDate();
        int length = movie.getLength();
        if(title == null || title.isEmpty()){
            return false;
        }
        if(description == null || description.isEmpty()){
            return false;
        }
        if(date == null){
            return false;
        }
        if(!this.lengthValidate(length)){
            return false;
        }
        return true;
    }

    private boolean lengthValidate(int length){
        return length >= 0 && length <= 300;
    }
}
