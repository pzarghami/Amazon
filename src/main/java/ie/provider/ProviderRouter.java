package ie.provider;

import ie.Router;
import io.javalin.Javalin;

public class ProviderRouter extends Router{
    private ProviderController controller ;
    public ProviderRouter(){this.controller = new ProviderController();}

    @Override
    public void addRoutes(Javalin javalin){
        //todo
    }
}
