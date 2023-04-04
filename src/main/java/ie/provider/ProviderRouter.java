package ie.provider;

import ie.Router;

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
