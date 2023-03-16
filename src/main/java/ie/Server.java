package ie;

import ie.exeption.ObjectNotFoundException;
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
        javalinServer.exception(Exception.class, this::commonExceptionHandler);
        javalinServer.exception(ValidationException.class, Controller::Exception403Handler);
        javalinServer.exception(NotFoundResponse.class, Controller::Exception404Handler);
    }

    public void runServer() {
        javalinServer.start("localhost", 8080);
    }
    public void stopServer() { javalinServer.stop(); }
    private void commonExceptionHandler(Exception e, Context ctx) {
        if(e instanceof ObjectNotFoundException) {
            Controller.Exception404Handler(e, ctx);
        } else {
            Controller.Exception403Handler(e, ctx);
        }
    }
}
