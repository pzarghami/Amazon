package ie;



import com.fasterxml.jackson.core.JsonProcessingException;
import ie.commodity.CommodityManager;
import ie.user.UserManager;
import ie.provider.ProviderManager;

import java.util.Objects;


public class Baloot {
    private final UserManager userManager;
    private final ProviderManager providerManager;
    private final CommodityManager commodityManager;

    public Baloot(){
        this.userManager = new UserManager(this);
        this.providerManager = new ProviderManager(this);
        this.commodityManager= new CommodityManager(this);
    }
    public void RunCommand(String command,String data){
        try{
            String res="";
            if(Objects.equals(command,"addUser")){
                res = userManager.updateOrAddUser(data);
            }
            else if(Objects.equals(command,"addProvider")){
                res = providerManager.updateOrAddProvider(data);
            }
            else if(Objects.equals(command,"addCommodity")){
                System.out.println(data);
                res=commodityManager.addCommodity(data);
//todo
            }
            else if(Objects.equals(command,"getCommoditiesList")){
                System.out.println(data);
//todo
            }
            else if(Objects.equals(command,"rateCommodity")){
                System.out.println(data);
//todo
            }
            else if(Objects.equals(command,"addToBuyList")){
                System.out.println(data);
//todo
            }
            else if(Objects.equals(command,"removeFromBuyList")){
                System.out.println(data);
//todo
            }
            else if(Objects.equals(command,"getCommodityById")){
                System.out.println(data);
//todo
            }
            else if(Objects.equals(command,"getCommodityByCategory")){
                System.out.println(data);
//todo
            }
            else if(Objects.equals(command,"getBuyList")){
                System.out.println(data);
//todo
            }
            else{
                throw new CustomException("InvalidCommand");
//todo
            }

//todo
            System.out.println(res);

        } catch (CustomException e) {
            System.out.println(e.getMessage());
//todo
        } catch (Exception e) {
            System.out.println("yes");
        }
    }
    public boolean isProviderExists(int id){
        return true;
    }


}