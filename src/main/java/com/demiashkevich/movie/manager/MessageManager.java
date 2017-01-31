package com.demiashkevich.movie.manager;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {

    private static final String PATH_TEXT = "properties.text";

    public static String getKey(String key, Locale locale) {
        return ResourceBundle.getBundle(PATH_TEXT, locale).getString(key);
    }

}
