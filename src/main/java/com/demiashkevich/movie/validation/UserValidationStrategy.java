package com.demiashkevich.movie.validation;

import com.demiashkevich.movie.entity.User;

import static com.demiashkevich.movie.validation.ItemValidation.emailValidation;
import static com.demiashkevich.movie.validation.ItemValidation.nameValidation;
import static com.demiashkevich.movie.validation.ItemValidation.passwordValidation;

public class UserValidationStrategy implements ValidationStrategy<User> {

    @Override
    public boolean validate(User user) {
        return nameValidation(user.getFirstName()) && nameValidation(user.getLastName()) &&
                passwordValidation(user.getPassword()) && emailValidation(user.getEmail());
    }

}
