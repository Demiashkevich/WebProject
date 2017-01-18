package com.demiashkevich.movie.command;

public enum EnumCommand {

    SIGN_IN( new SignInCommand() ),
    SIGN_UP( new SignUpCommand() ),
    CREATE_MOVIE( new CreateMovieCommand() ),
    CREATE_ACTOR( new CreateActorCommand() ),
    ADD_MOVIE( new AddMovieCommand() ),
    ADD_ACTOR( new AddActorCommand() ),
    ADD_REVIEW( new AddReviewCommand() ),
    SHOW_MOVIES( new ShowMoviesCommand() ),
    SHOW_ACTORS( new ShowActorsCommand() ),
    SHOW_MOVIE( new ShowMovieCommand() ),
    SHOW_ACTOR( new ShowActorCommand() ),
    DELETE_MOVIE( new DeleteMovieCommand() ),
    DELETE_ACTOR( new DeleteActorCommand() ),
    DELETE_REVIEW( new DeleteReviewCommand() ),
    CHANGE_LANGUAGE( new ChangeLanguageCommand() ),
    EDIT_REVIEW( new EditReviewCommand() ),
    UPDATE_REVIEW( new UpdateReviewCommand() ),
    EDIT_MOVIE( new EditMovieCommand() ),
    LOGOUT( new LogoutCommand() ),
    UPDATE_MOVIE( new UpdateMovieCommand() );

    private Command command;

    EnumCommand(Command command){
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
