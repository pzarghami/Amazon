package Baloot.model;

import Baloot.Exeption.CustomException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class User {

    private String username;
    private String password;
    private String email;
    private String birthDate;
    private String address;
    private Discount discount;
    private int credit;

    private  Map<Commodity,Integer> buyList;
    private  Map<Commodity,Integer> userPurchasedList;
    private  ArrayList<Discount> discountCodeUsed;

    public User(String username, String password, String email, String birthDate,String address,int credit){
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.address = address;
        this.credit = credit;
        this.buyList = new HashMap<>();
        this.userPurchasedList = new HashMap<>();
        this.discountCodeUsed = new ArrayList<>();
        this.discount = null;
    }

    public String getUsername(){return username;}

    public void addToBuyList(Commodity commodity){
        if(buyList.containsKey(commodity)){
            buyList.put(commodity,buyList.get(commodity)+1);
        }
        else{
            buyList.put(commodity,1);
        }
    }
    public void removeFromBuyList(Commodity commodity) throws CustomException {
        if(!buyList.containsKey(commodity))
            throw new CustomException("don't exist.");
        if(buyList.get(commodity)>1)
            buyList.put(commodity,buyList.get(commodity)-1);
        else
            buyList.remove(commodity);
    }
    public void finalizeThePurchase() throws CustomException {
        var buyListPrice = getBuyListPrice();
        if(buyListPrice > this.credit)
            throw new CustomException("credit is not enough");
        userPurchasedList.putAll(buyList);
        if(discount != null){
            this.discountCodeUsed.add(discount);
            this.discount = null;
        }
    }

    public void addCredit(int amount){this.credit += amount;}

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
    public void setDiscount(Discount discount) throws CustomException {
        if(discountCodeUsed.contains(discount))
            throw new CustomException("discount is used");
        this.discount = discount;
    }
    public float getBuyListPrice(){
        float sumPrice = 0;
        for(Commodity commodity : buyList.keySet()){
            sumPrice += commodity.getPrice();
        }
        return sumPrice;
    }
}
