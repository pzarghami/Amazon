package ie.user.controllers;


import ie.Baloot;
import ie.Constant;
import ie.Controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(Constant.URLS.CREDIT)
public class AddCredit extends Controller{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
        if(Baloot.isLoggedIn())
            request.getRequestDispatcher(Constant.JSP.CREDIT).forward(request,response);
        else
            response.sendRedirect(Constant.URLS.LOGIN);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        var creditASText = request.getParameter("credits");
        int credit=Integer.parseInt(creditASText);

        Map<String,String> errorMessages = new HashMap<>();

        if(Baloot.isLoggedIn()) {
            if (credit < 0) {
                errorMessages.put("InvalidCredit", "credit is negative");
            } else {
                Baloot.addCredit(credit);
            }
        }
        if(!errorMessages.isEmpty()) {
            send404Response(request, response, errorMessages);
        }
        else {
            request.getRequestDispatcher(Constant.JSP._200).forward(request,response);
        }

    }
}
