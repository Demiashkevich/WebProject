package com.demiashkevich.movie.validation;

import com.demiashkevich.movie.entity.Actor;

import static com.demiashkevich.movie.validation.ItemValidation.nameValidation;

public class ActorValidationStrategy implements ValidationStrategy<Actor> {

    @Override
    public boolean validate(Actor actor) {
        return nameValidation(actor.getFirstName()) && nameValidation(actor.getLastName());
    }

}
