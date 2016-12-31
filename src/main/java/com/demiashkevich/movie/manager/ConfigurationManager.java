package com.demiashkevich.movie.manager;

import java.util.ResourceBundle;

public class ConfigurationManager {

    private  static ResourceBundle resourceBundle = ResourceBundle.getBundle("configuration");

    public static String getKey(String key){
        return resourceBundle.getString(key);
    }

}
