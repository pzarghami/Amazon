package ie;

import ie.Constant;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public abstract class Controller extends HttpServlet {

    public String[] splitPathParams(String pathInfo) {
        if (pathInfo == null || pathInfo.equals("/"))
            return null;
        return pathInfo.replace("/", " ").trim().split(" ");
    }

    public void send404Response(HttpServletRequest request, HttpServletResponse response, Map<String, String> errorMessages)
            throws ServletException, IOException {

        request.setAttribute("errors", View.getHtmlList(errorMessages));
        response.setStatus(404);
        request.getRequestDispatcher(Constant.JSP._404_).forward(request, response);
    }
    public void sendBadRequestResponse(HttpServletRequest request, HttpServletResponse response, Map<String, String> errorMessages)
            throws ServletException, IOException {
        request.setAttribute("errors", View.getHtmlList(errorMessages));
        response.setStatus(400);
        request.getRequestDispatcher(Constant.JSP.ERROR).forward(request, response);
    }
}
