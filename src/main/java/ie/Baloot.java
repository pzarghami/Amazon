package ie;



import java.util.Objects;


public class Baloot {
    public Baloot(){}
    public void RunCommand(String command,String data){
        try{
            if(Objects.equals(command,"addUser")){
                System.out.println(data);
                //todo
            }
            else if(Objects.equals(command,"addProvider")){
                System.out.println(data);
                //todo
            }
            else if(Objects.equals(command,"addCommodity")){
                System.out.println(data);
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
        } catch (CustomException e) {
            System.out.println(e.getMessage());
            //todo
        }
    }
}
