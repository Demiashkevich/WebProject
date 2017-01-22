package com.demiashkevich.movie.factory;

import com.demiashkevich.movie.command.Command;
import com.demiashkevich.movie.command.EmptyCommand;
import com.demiashkevich.movie.command.EnumCommand;

import javax.servlet.http.HttpServletRequest;

public class ActionCommand {

    private static final String COMMAND = "command";

    static public Command defineCommand(HttpServletRequest request){
        Command command = new EmptyCommand();

        String commandName = request.getParameter(COMMAND);
        if(commandName == null || commandName.isEmpty()){
            return command;
        }
        EnumCommand enumCommand = EnumCommand.valueOf(commandName.toUpperCase());
        command = enumCommand.getCommand();
        return command;
    }

}