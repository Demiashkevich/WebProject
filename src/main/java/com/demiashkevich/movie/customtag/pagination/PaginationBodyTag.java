package com.demiashkevich.movie.customtag.pagination;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class PaginationBodyTag extends TagSupport {

    private static final Logger LOGGER = Logger.getLogger(PaginationBodyTag.class);

    private int currentPage;
    private int countPage;

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setCountPage(int countPage) {
        this.countPage = countPage;
    }

    @Override
    public int doStartTag() throws JspException {
        String bodyTag = "<table class=pagination-table><tr>";
        for(int i = 1; i <= countPage; i++ ){
            if(currentPage == i){
                bodyTag += "<td class=pagination-cp>" + i + "</td>";
            } else {
                bodyTag += "<td><a class=pagination-link-page href=movie?command=show_movies&currentPage=" + i + ">" + i + "</a></td>";
            }
        }
        bodyTag += "</tr></table>";
        try {
            pageContext.getOut().write(bodyTag);
        } catch (IOException exception) {
            LOGGER.error(exception);
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

}
