package com.demiashkevich.movie.validation;

import com.demiashkevich.movie.entity.Actor;

public class ActorValidation implements Validation<Actor> {

    private static final String REGEX_NAME = "[A-zА-яЁё]+";

    @Override
    public boolean execute(Actor actor) {
        String firstName = actor.getFirstName();
        String lastName = actor.getLastName();
        String biography = actor.getBiography();
        if(firstName == null || firstName.isEmpty() || !this.partOfNameValidate(firstName)) {
            return false;
        }
        if(lastName == null || lastName.isEmpty() || !this.partOfNameValidate(lastName)){
            return false;
        }
        if(biography == null || biography.isEmpty()){
            return false;
        }
        return true;
    }

    private boolean partOfNameValidate(String firstName){
        return firstName.matches(REGEX_NAME);
    }


}
