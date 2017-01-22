package com.demiashkevich.movie.customtag.button;

import com.demiashkevich.movie.command.EnumCommand;
import com.demiashkevich.movie.memento.RequestParameter;
import com.demiashkevich.movie.memento.RequestParameterList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class ButtonSnapshotTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        try {
            RequestParameterList parameters = (RequestParameterList)pageContext.getSession().getAttribute("parameters");
            RequestParameter parameter = parameters.pollLast();
            if(parameter != null) {
                EnumCommand command = parameter.getCommand();
                switch (command) {
                    case SHOW_MOVIE: {
                        String movieId = parameter.get("movie_id");
                        if(movieId == null){
                            this.doStartTag();
                        }
                        pageContext.getOut().write("<a class=ss-button href=movie?command=" + command.name().toLowerCase() + "&movie_id=" + movieId + ">");
                    } break;
                    case SHOW_MOVIES:
                    case SHOW_ACTORS: {
                        String currentPage = parameter.get("currentPage");
                        pageContext.getOut().write("<a class=ss-button href=movie?command=" + command.name().toLowerCase() + "&currentPage=" + currentPage + ">");
                    } break;
                    case SHOW_ACTOR: {
                        String actorId = parameter.get("actor_id");
                        if (actorId == null) {
                            this.doStartTag();
                        }
                        pageContext.getOut().write("<a class=ss-button href=movie?command=" + command.name().toLowerCase() + "&actor_id=" + actorId + ">");
                    } break;
                    default: {
                        pageContext.getOut().write("<a class=ss-button href=movie?command=" + command.name().toLowerCase() + ">");
                    }
                }
            } else {
                pageContext.getOut().write("<a class=ss-button href=movie>");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            pageContext.getOut().write("</a>");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }
}
