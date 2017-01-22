package com.demiashkevich.movie.customtag.pagination;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class PaginationNextTag extends TagSupport{

    private int currentPage;
    private int countPage;
    private boolean flag = true;

    public void setCountPage(int countPage) {
        this.countPage = countPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    @Override
    public int doStartTag() throws JspException {
        if(countPage != 1 && currentPage != countPage){
            flag = true;
            try {
                pageContext.getOut().write("<a class=pagination-link href=movie?command=show_movies&currentPage=" + (currentPage + 1) + ">");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return EVAL_BODY_INCLUDE;
        }
        flag = false;
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        if(flag) {
            try {
                pageContext.getOut().write("</a>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return EVAL_PAGE;
    }
}
