package Baloot.model.DTO;

import Baloot.model.Commodity;

import java.util.ArrayList;

public class UserDTO {
    private String username;
    private String email;
    private String birthDate;
    private String address;
    private int credit;
    private ArrayList<CommodityBriefDTO> buyList;
    private ArrayList<CommodityBriefDTO> userPurchasedList;

    public void setUsername(String username){
        this.username = username;
    }
    public void setEmail(String email){this.email = email;}
    public void setBirthDate(String birthDate){
        this.birthDate = birthDate;
    }
    public void setAddress(String address){this.address = address;}
    public void setCredit(int credit){
        this.credit = credit;
    }
    public void setBuyList(ArrayList<CommodityBriefDTO>buyList){this.buyList = buyList;}
    public void setUserPurchasedList(ArrayList<CommodityBriefDTO>userPurchasedList){this.userPurchasedList = userPurchasedList;}
    public String getUsername(){return this.username;}
    public String getEmail(){
        return this.email;
    }
    public String getBirthDate(){
        return this.birthDate;
    }
    public String getAddress(){
        return this.address;
    }
    public int getCredit(){
        return this.credit;
    }
    public ArrayList<CommodityBriefDTO>getBuyList(){return this.buyList;}
    public ArrayList<CommodityBriefDTO> getUserPurchasedList() {return this.userPurchasedList;}
}
