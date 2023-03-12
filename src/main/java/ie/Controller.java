package ie;

import io.javalin.http.Context;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class Controller {
    public static void Exception404Handler(Exception e, Context ctx) {
        try {
            ctx.html(new String(Files.readAllBytes(Paths.get("src/main/resources/404.html")))).status(404);
        } catch (IOException ex) {
            ctx.html("<h1>404 Page not found</h1>").status(404);
        }
    }
    public static void Exception403Handler(Exception e, Context ctx) {
        try {
            ctx.html(new String(Files.readAllBytes(Paths.get("src/main/resources/403.html")))).status(403);
        } catch (IOException ex) {
            ctx.html("<h1>404 Page not found</h1>").status(403);
        }
    }
}