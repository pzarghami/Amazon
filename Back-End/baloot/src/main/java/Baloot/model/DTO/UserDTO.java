package Baloot.model.DTO;

import java.util.ArrayList;

public class UserDTO {
    private String username;
    private String password;
    private String email;
    private String birthDate;
    private String address;
    private String discountCode;
    private int credit;
    private ArrayList<Integer> buyList;
    private ArrayList<String> discountCodeUsed;
    private ArrayList<String> userBuyList;
    private ArrayList<String> userPurchasedList;

    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setBirthDate(String birthDate){
        this.birthDate = birthDate;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public void setDiscountCode(String discountCode){
        this.discountCode = discountCode;
    }
    public void setCredit(int credit){
        this.credit = credit;
    }
    public void setBuyList(ArrayList<Integer>buyList){
        this.buyList = buyList;
    }
    public void setDiscountCodeUsed(ArrayList<String>discountCodeUsed){
        this.discountCodeUsed = discountCodeUsed;
    }
    public void setUserBuyList(ArrayList<String>userBuyList){
        this.userBuyList = userBuyList;
    }
    public void setUserPurchasedList(ArrayList<String>userPurchasedList){
        this.userPurchasedList = userPurchasedList;
    }
    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
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
    public ArrayList<Integer>getBuyList(){
        return this.buyList;
    }

    public ArrayList<String> getUserBuyList() {
        return this.userBuyList;
    }

    public ArrayList<String> getUserPurchasedList() {
        return this.userPurchasedList;
    }

    public ArrayList<String> getDiscountCodeUsed() {
        return this.discountCodeUsed;
    }
}
