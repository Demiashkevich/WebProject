package com.demiashkevich.movie.command;

public enum EnumCommand {

    //GUEST, USER COMMAND
    EMPTY ( new EmptyCommand() ),
    SIGN_IN( new SignInCommand() ),
    SIGN_UP( new SignUpCommand() ),
    SHOW_MOVIES( new ShowMoviesCommand() ),
    SHOW_ACTORS( new ShowActorsCommand() ),
    SHOW_MOVIE( new ShowMovieCommand() ),
    SHOW_ACTOR( new ShowActorCommand() ),
    DELETE_REVIEW( new DeleteReviewCommand() ),
    CHANGE_LANGUAGE( new ChangeLanguageCommand() ),
    EDIT_REVIEW( new EditReviewCommand() ),
    UPDATE_REVIEW( new UpdateReviewCommand() ),
    LOGOUT( new LogoutCommand() ),
    REGISTRATION( new RegistrationCommand() ),
    AUTHORIZATION( new AuthorizationCommand() ),
    CREATE_REVIEW( new CreateReviewCommand() ),
    ADD_REVIEW( new AddReviewCommand() ),
    SHOW_PRIVATE_OFFICE( new ShowPrivateOfficeCommand() ),

    //ADMINISTRATOR COMMAND
    EDIT_MOVIE( new EditMovieCommand() ),
    ADD_MOVIE( new AddMovieCommand() ),
    ADD_ACTOR( new AddActorCommand() ),
    DELETE_MOVIE( new DeleteMovieCommand() ),
    DELETE_ACTOR( new DeleteActorCommand() ),
    UPDATE_MOVIE( new UpdateMovieCommand() ),
    SHOW_USERS( new ShowUsersCommand() ),
    EDIT_USER_STATUS( new EditUserStatusCommand() ),
    CREATE_MOVIE( new CreateMovieCommand() ),
    CREATE_ACTOR( new CreateActorCommand() ),;

    private Command command;

    EnumCommand(Command command){
        this.command = command;
    }


    /**
     * @return object created command
     */
    public Command getCommand() {
        return command;
    }
}
