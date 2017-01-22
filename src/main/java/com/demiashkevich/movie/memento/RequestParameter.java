package com.demiashkevich.movie.memento;

import com.demiashkevich.movie.command.EnumCommand;

import java.util.HashMap;

public class RequestParameter {

    private EnumCommand command;
    private HashMap<String, String> parameters = new HashMap<>();

    public EnumCommand getCommand() {
        return command;
    }

    public void setCommand(EnumCommand command) {
        this.command = command;
    }

    public String put(String key, String value) {
        return parameters.put(key, value);
    }

    public String get(String key) {
        return parameters.get(key);
    }
}
