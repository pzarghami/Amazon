package ie;
import io.javalin.Javalin;

public abstract class Router {
    public abstract void addRoutes(Javalin javalin);
}
