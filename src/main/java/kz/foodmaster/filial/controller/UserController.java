package kz.foodmaster.filial.controller;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import kz.foodmaster.filial.business.User;
import kz.foodmaster.filial.data.UserDB;

public class UserController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        String requestURI = request.getRequestURI();
        String url = "";
        if (requestURI.endsWith("/deleteCookies")) {
            url = deleteCookies(request, response);
        }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        String requestURI = request.getRequestURI();
        String url = "";
        if (requestURI.endsWith("/subscribeToLogin")) {
            url = subscribeToLogin(request, response);
        }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    private String deleteCookies(HttpServletRequest request,
            HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);  //delete the cookie
            cookie.setPath("/");  //entire application
            response.addCookie(cookie);
        }
        return "/delete_cookies.jsp";
    }

    private String subscribeToLogin(HttpServletRequest request,
            HttpServletResponse response) {

        String login = request.getParameter("login");
        String pass = request.getParameter("pass");
        String roleID = request.getParameter("roleID");

        User user = new User();
        user.setUserLogin(login);
        user.setUserPass(pass);
        user.setUserRoleId(Integer.parseInt(roleID));

        request.setAttribute("user", user);

        String url;
        String message;
        //check that email address doesn't already exist
        if (UserDB.loginExists(login)) {
            message = "This login already exists. <br>"
                    + "Please enter another login.";
            request.setAttribute("message", message);
            url = "/login/index.jsp";
        } else {
            UserDB.insert(user);
            message = "";
            request.setAttribute("message", message);
            url = "/login/thanks.jsp";
        }
        return url;
    }
}
