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

@WebFilter(urlPatterns = "/movie", initParams = {
        @WebInitParam(name = "PAGE_SIGN_IN", value = "path.page.authorization"),
        @WebInitParam(name = "PAGE_PRIVATE_OFFICE", value = "path.page.private_office")
})
public class ServletSecurityRoleFilter implements Filter{

    private String pageSignIn;
    private String pagePrivateOffice;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        pageSignIn = filterConfig.getInitParameter("PAGE_SIGN_IN");
        pageSignIn = ConfigurationManager.getKey(pageSignIn);
        pagePrivateOffice = filterConfig.getInitParameter("PAGE_PRIVATE_OFFICE");
        pagePrivateOffice = ConfigurationManager.getKey(pagePrivateOffice);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        HttpSession session = request.getSession();

        ClientType role = (ClientType)session.getAttribute("role");
        String commandName = request.getParameter("command");

        EnumCommand commandCreateReview = EnumCommand.CREATE_REVIEW;
        if(commandName != null) {
            if (ClientType.GUEST == role) {
                EnumCommand commandRequest = EnumCommand.valueOf(commandName.toUpperCase());
                if (commandCreateReview == commandRequest) {
                    RequestDispatcher requestDispatcher = request.getServletContext().getRequestDispatcher(pageSignIn);
                    requestDispatcher.forward(request, response);
                    return;
                }
            }
            EnumCommand commandAuthorization = EnumCommand.AUTHORIZATION;
            if (ClientType.USER == role || ClientType.ADMINISTRATOR == role) {
                EnumCommand commandRequest = EnumCommand.valueOf(commandName.toUpperCase());
                if (commandAuthorization == commandRequest) {
                    RequestDispatcher requestDispatcher = request.getServletContext().getRequestDispatcher(pagePrivateOffice);
                    requestDispatcher.forward(request, response);
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
