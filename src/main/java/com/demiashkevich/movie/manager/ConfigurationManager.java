package com.demiashkevich.movie.manager;

import java.util.ResourceBundle;

public class ConfigurationManager {

    private static final String PATH_CONFIGURATION = "configuration";

    private  static ResourceBundle resourceBundle = ResourceBundle.getBundle(PATH_CONFIGURATION);

    public static String getKey(String key){
        return resourceBundle.getString(key);
    }

}
