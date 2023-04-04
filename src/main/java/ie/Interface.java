package ie;

import com.fasterxml.jackson.core.JsonProcessingException;
import ie.exeption.CustomException;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class Interface implements ServletContextListener{
    private Baloot baloot;
    public void contextInitialized(ServletContextEvent event) {
        baloot = new Baloot();
        try {
            baloot.fetchData();
        } catch (CustomException e) {
            // TODO : handle exception
            e.printStackTrace();
        }
        event.getServletContext().setAttribute("baloot", baloot);
    }
}
