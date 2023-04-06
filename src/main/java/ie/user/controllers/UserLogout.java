package ie.user.controllers;

import ie.Baloot;
import ie.Constant;
import ie.Controller;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(
        name = "UserLogoutServlet",
        urlPatterns = Constant.URLS.LOGOUT
)
public class UserLogout extends Controller {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Baloot.logoutUser();
        response.sendRedirect(Constant.URLS.ROOT);
    }
}