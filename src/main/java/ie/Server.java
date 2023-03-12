package ie;

import io.javalin.Javalin;
import io.javalin.core.validation.ValidationException;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

public class Server {
    private final Javalin javalinServer;
    public Server(Router[] routers){
        javalinServer = Javalin.create();
        for (var router : routers){
            router.addRoutes(javalinServer);
        }
    }
}
