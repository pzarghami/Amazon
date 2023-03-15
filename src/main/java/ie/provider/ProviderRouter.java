package ie.provider;

import ie.Router;
import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class ProviderRouter extends Router{
    private ProviderController controller ;
    public ProviderRouter(){this.controller = new ProviderController();}

    @Override
    public void addRoutes(Javalin javalin) {
        javalin.routes(() -> {
            path("/providers", () -> {
                path("{provider_id}", () -> {
                    get(controller::providerHandler);
                });
            });
        });
    }
}
