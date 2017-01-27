package com.demiashkevich.movie.filter;

import com.demiashkevich.movie.memento.RequestParameterList;
import com.demiashkevich.movie.type.ClientType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

@WebFilter(urlPatterns = "/movie", servletNames = {"Controller"})
public class ServletDefaultFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        HttpSession session = request.getSession();
        ClientType type = (ClientType)session.getAttribute("role");
        Locale locale = (Locale)session.getAttribute("locale");
        RequestParameterList parameters = (RequestParameterList)session.getAttribute("parameters");
        if(type == null){
            type = ClientType.GUEST;
            session.setAttribute("role", type);
        }
        if(locale == null){
            locale = Locale.getDefault();
            session.setAttribute("locale", locale);
        }
        if(parameters == null){
            parameters = new RequestParameterList();
            session.setAttribute("parameters", parameters);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
