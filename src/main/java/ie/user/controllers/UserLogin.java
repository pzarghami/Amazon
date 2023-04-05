package ie.user.controllers;

import ie.Baloot;
import ie.Constant;
import ie.Controller;
import ie.exeption.CustomException;
import ie.exeption.ObjectNotFoundException;
import ie.user.UserManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;
@WebServlet(
        name = "UserLoginServlet",
        urlPatterns = Constant.URLS.LOGIN
)
public class UserLogin  extends Controller {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher(Constant.JSP.LOGIN).forward(request, response);
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        var username = request.getParameter("username");
        var pass=request.getParameter("password");
        Map<String,String> errorMessages = new HashMap<>();
        // business logic
        try {
            var user= UserManager.getInstance().getElementById(username);
            user.isYourPassword(pass);
            Baloot.loginUser(username);

        } catch (CustomException e) {
            errorMessages.put("InvalidUser", "User Not Found");
        }
        // results
        if(!errorMessages.isEmpty()) {
            send404Response(request, response, errorMessages);
        }
        else {
            response.sendRedirect(Constant.URLS.ROOT);
        }

    }
}
