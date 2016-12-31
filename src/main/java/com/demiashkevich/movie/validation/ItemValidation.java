package com.demiashkevich.movie.validation;

public class ItemValidation {

    public static boolean nameValidation(String partOfName){
        return partOfName.matches("");
    }

    public static boolean emailValidation(String email){
        return email.matches("");
    }

    public static boolean dateValidation(String date){
        return date.matches("");
    }

    public static boolean lengthValidation(String length){
        return length.matches("");
    }

    public static boolean passwordValidation(String password) {
        return  password.matches("");
    }

}
