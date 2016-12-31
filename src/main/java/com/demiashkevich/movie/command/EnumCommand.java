package com.demiashkevich.movie.command;

public enum EnumCommand {

    SIGN_IN( new SignInCommand() ),
    SIGN_UP( new SignUpCommand() ),
    ADD_MOVIE( new AddMovieCommand() ),
    ADD_ACTOR( new AddActorCommand() ),
    SHOW_MOVIE( new ShowMovieCommand() );

    private Command command;

    EnumCommand(Command command){
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
