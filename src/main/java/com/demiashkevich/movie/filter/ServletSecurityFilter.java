package com.demiashkevich.movie.filter;

import com.demiashkevich.movie.command.EnumCommand;
import com.demiashkevich.movie.manager.ConfigurationManager;
import com.demiashkevich.movie.type.ClientType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumSet;

@WebFilter(urlPatterns = {"/movie", "/jsp/security/*"}, initParams = {
        @WebInitParam(name = "PAGE_ERROR", value = "path.page.error")
})
public class ServletSecurityFilter implements Filter {

    private String pageError;
    private static final String MESSAGE = "";//DESCRIPTION

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        pageError = filterConfig.getInitParameter("PAGE_ERROR");
        pageError = ConfigurationManager.getKey(pageError);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        HttpSession session = request.getSession(true);
        ClientType type = (ClientType)session.getAttribute("role");
        String commandName = request.getParameter("command");
        EnumSet<EnumCommand> commands = EnumSet.range(EnumCommand.EDIT_MOVIE, EnumCommand.CREATE_ACTOR);
        if(commandName != null) {
            EnumCommand command = EnumCommand.valueOf(commandName.toUpperCase());
            if(commands.contains(command) && type != ClientType.ADMINISTRATOR){
                request.setAttribute("error", MESSAGE);
                RequestDispatcher requestDispatcher = request.getServletContext().getRequestDispatcher(pageError);
                requestDispatcher.forward(request, response);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
