package com.demiashkevich.movie.controller;

import com.demiashkevich.movie.ActionCommand;
import com.demiashkevich.movie.command.Command;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/movie")
public class Controller extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.procedure(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.procedure(request, response);
    }

    private void procedure(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = ActionCommand.defineCommand(request);
        String page = command.execute(request);

        RequestDispatcher requestDispatcher = request.getServletContext().getRequestDispatcher(page);
        requestDispatcher.forward(request, response);
    }
}
