package ie.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ie.CustomException;

import java.util.ArrayList;

public class User {

    private String username;
    private String password;
    private String email;
    private String birthDate;
    private String address;
    private int credit;
    private ArrayList<Integer>buyList;

    @JsonCreator
    private User(){
        this.buyList=new ArrayList<>();
    }

    @JsonProperty(value = "username", required = true)
    private void setUsername(String username){
        this.username = username;
    }

    @JsonProperty(value = "password", required = true)
    private void setPassword(String password){
        this.password = password;
    }

    @JsonProperty(value = "email", required = true)
    private void setEmail(String email){
        this.email = email;
    }

    @JsonProperty(value = "birthDate", required = true)
    private void setBirthDate(String birthDate){
        this.birthDate = birthDate;
    }

    @JsonProperty(value = "address", required = true)
    private void setAddress(String address){
        this.address = address;
    }

    @JsonProperty(value = "credit", required = true)
    private void setUsername(int credit){
        this.credit = credit;
    }

    @JsonIgnore()
    public ArrayList<Integer> getBuyList() {
        return this.buyList;
    }

    @JsonGetter(value = "username")
    private String getUsername() {return this.username;}

    @JsonGetter(value = "password")
    private String getPassword() {return this.password;}

    @JsonGetter(value = "email")
    private String getEmail() {return this.email;}

    @JsonGetter(value = "birthDate")
    private String getBirthDate() {return this.birthDate;}

    @JsonGetter(value = "address")
    private String getAddress() {return this.address;}

    @JsonGetter(value = "credit")
    private int getCredit() {return this.credit;}

    public void addToBuyList(int commodityId)throws CustomException {
        if(buyList.contains(commodityId))
            throw new CustomException("already exists in buy list.");
        buyList.add(commodityId);
    }

    public void removeFromBuyList(int commodityId)throws CustomException{
        if(!buyList.contains(commodityId))
            throw new CustomException("commodity does not exists.");
        buyList.remove((Integer) commodityId);
    }
}
