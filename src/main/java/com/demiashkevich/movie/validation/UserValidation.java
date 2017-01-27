package com.demiashkevich.movie.validation;

import com.demiashkevich.movie.entity.User;

public class UserValidation implements Validation<User> {

    private static final String REGEX_NAME = "[A-zА-яЁё]+";
    private static final String REGEX_PASSWORD = "^((?=.*\\d+).*)((?=.*[a-z]+).*)((?=.*[A-Z]+).*)(.{6,})$";
    private static final String REGEX_EMAIL = ".+@.+\\..+";

    @Override
    public boolean execute(User user) {
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        String password = user.getPassword();

        if(firstName == null || firstName.isEmpty() || !this.partOfNameValidate(firstName)){
            return false;
        }
        if(lastName == null || lastName.isEmpty() || !this.partOfNameValidate(lastName)){
            return false;
        }
        if(email == null || email.isEmpty() || !this.emailValidate(email)){
            return false;
        }
        if(password == null || password.isEmpty() || !this.passwordValidate(password)){
            return false;
        }
        return true;
    }

    private boolean partOfNameValidate(String firstName){
        return firstName.matches(REGEX_NAME);
    }

    private boolean passwordValidate(String password){
        return password.matches(REGEX_PASSWORD);
    }

    private boolean emailValidate(String email){
        return email.matches(REGEX_EMAIL);
    }

}
