package com.automoto.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A servlet that handles requests for the "About" page of the AutoMoto application.
 * This servlet supports both GET and POST requests, forwarding the user to the about.jsp page.
 * 
 * @WebServlet Configures the servlet to be asynchronous and maps it to the "/about" URL pattern.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/about" })
public class About extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor. Initializes the servlet.
     */
    public About() {
        super();
    }

    /**
     * Handles HTTP GET requests. Forwards the request to the about.jsp page
     * located in the WEB-INF/pages directory.
     *
     * @param request  The HttpServletRequest object containing client request data.
     * @param response The HttpServletResponse object for sending responses.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs during request handling.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/pages/about.jsp").forward(request, response);
    }

    /**
     * Handles HTTP POST requests by delegating to the doGet method.
     * This ensures consistent behavior for both GET and POST requests.
     *
     * @param request  The HttpServletRequest object containing client request data.
     * @param response The HttpServletResponse object for sending responses.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs during request handling.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}