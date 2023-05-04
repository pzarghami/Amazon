package Baloot.model.DTO;

import Baloot.model.Commodity;

import java.util.ArrayList;

public class UserDTO {
    private String username;
    private String password;
    private String email;
    private String birthDate;
    private String address;
    private String discountCode;
    private int credit;
    private ArrayList<Commodity> buyList;
    private ArrayList<String> discountCodeUsed;
    private ArrayList<Commodity> userPurchasedList;

    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setEmail(String email){this.email = email;}
    public void setBirthDate(String birthDate){
        this.birthDate = birthDate;
    }
    public void setAddress(String address){this.address = address;}
    public void setDiscountCode(String discountCode){
        this.discountCode = discountCode;
    }
    public void setCredit(int credit){
        this.credit = credit;
    }
    public void setBuyList(ArrayList<Commodity>buyList){this.buyList = buyList;}
    public void setDiscountCodeUsed(ArrayList<String>discountCodeUsed){this.discountCodeUsed = discountCodeUsed;}
    public void setUserPurchasedList(ArrayList<Commodity>userPurchasedList){this.userPurchasedList = userPurchasedList;}
    public String getUsername(){return this.username;}
    public String getPassword(){return this.password;}
    public String getEmail(){
        return this.email;
    }
    public String getBirthDate(){
        return this.birthDate;
    }
    public String getAddress(){
        return this.address;
    }
    public String getDiscountCode(){
        return this.discountCode;
    }
    public int getCredit(){
        return this.credit;
    }
    public ArrayList<Commodity>getBuyList(){return this.buyList;}
    public ArrayList<Commodity> getUserPurchasedList() {return this.userPurchasedList;}
    public ArrayList<String> getDiscountCodeUsed() {
        return this.discountCodeUsed;
    }
}
