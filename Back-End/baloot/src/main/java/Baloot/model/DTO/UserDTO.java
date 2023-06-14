package Baloot.model.DTO;

import Baloot.model.Commodity;

import java.util.ArrayList;

public class UserDTO {
    private String username;
    private String email;
    private String birthDate;
    private String address;
    private String nickName;
    private int credit;
    private ArrayList<CommodityDTO> buyList;
    private ArrayList<CommodityDTO> userPurchasedList;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCredit() {
        return this.credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public ArrayList<CommodityDTO> getBuyList() {
        return this.buyList;
    }

    public void setBuyList(ArrayList<CommodityDTO> buyList) {
        this.buyList = buyList;
    }

    public ArrayList<CommodityDTO> getUserPurchasedList() {
        return this.userPurchasedList;
    }

    public void setUserPurchasedList(ArrayList<CommodityDTO> userPurchasedList) {
        this.userPurchasedList = userPurchasedList;
    }

    public void setNickname(String login) {
        nickName=login;
    }

    public void setPassword(Object o) {
    }
}
