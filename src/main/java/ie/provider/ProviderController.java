package ie.provider;


import ie.Controller;
import ie.CustomException;
import ie.comment.CommentManager;
import ie.commodity.CommodityManager;
import io.javalin.http.Context;
import java.io.IOException;

public class ProviderController extends Controller{
    private ProviderView viewHandler;
    public ProviderController(){this.viewHandler = new ProviderView();}

    public void providerHandler(Context ctx)throws CustomException,IOException {
        var providerId = ctx.pathParamAsClass("{provider_id}",Integer.class).get();
        var provider = ProviderManager.getInstance().getElementById(providerId.toString());
        var commodotiesList = CommodityManager.getInstance().getElementsById(provider.getCommoditiesList());
        ctx.html(viewHandler.getProviderHtmlResponse(provider,commodotiesList));
    }
}
