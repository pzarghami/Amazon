package ie.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ie.Constant;
import ie.discount.DiscountManager;
import ie.commodity.Commodity;
import ie.exeption.CustomException;
import ie.commodity.CommodityManager;

import java.util.ArrayList;
import java.util.Objects;

public class User {

    private String username;
    private String password;
    private String email;
    private String birthDate;
    private String address;
    private String discountCode;
    private int credit;

    private final ArrayList<Integer> buyList;
    private final ArrayList<String> discountCodeUsed;
    private final ArrayList<String> userBuyList;
    private final ArrayList<String> userPurchasedList;

    @JsonCreator
    private User() {

        this.buyList = new ArrayList<>();
        this.userBuyList = new ArrayList<>();
        this.userPurchasedList = new ArrayList<>();
        this.discountCodeUsed = new ArrayList<>();
        this.discountCode = "";
        this.userBuyList.add("1");
        this.userBuyList.add("2");
        this.userBuyList.add("3");
    }

    @JsonProperty(value = "username", required = true)
    private void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty(value = "password", required = true)
    private void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty(value = "email", required = true)
    private void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty(value = "birthDate", required = true)
    private void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @JsonProperty(value = "address", required = true)
    private void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty(value = "credit", required = true)
    private void setUsername(int credit) {
        this.credit = credit;
    }

    @JsonIgnore()
    public ArrayList<Integer> getBuyList() {
        return this.buyList;
    }

    @JsonGetter(value = "username")
    public String getUsername() {
        return this.username;
    }

    @JsonGetter(value = "password")
    private String getPassword() {
        return this.password;
    }

    @JsonGetter(value = "email")
    public String getEmail() {
        return this.email;
    }

    @JsonGetter(value = "birthDate")
    public String getBirthDate() {
        return this.birthDate;
    }

    @JsonGetter(value = "address")
    public String getAddress() {
        return this.address;
    }

    @JsonGetter(value = "credit")
    public int getCredit() {
        return this.credit;
    }

    @JsonIgnore()
    public ArrayList<String> getUserBuyList() {
        return this.userBuyList;
    }

    @JsonIgnore()
    public ArrayList<String> getUserPurchasedList() {
        return this.userPurchasedList;
    }


    public void addToUserBuyList(String commodityId) throws CustomException {
        CommodityManager.getInstance().getElementById(commodityId).buy();
        this.userBuyList.add(commodityId);
    }

    public float getCurrentBuyListPrice() throws CustomException {
        var commodityManager = CommodityManager.getInstance();
        float sum = 0;
        for (var commodityId : this.userBuyList) {
            var commodity = commodityManager.getElementById(commodityId);
            sum = sum + commodity.getPrice();
        }
        if(!discountCode.equals("")){
        var discount = DiscountManager.getInstance().getElementById(discountCode).getDiscountAmount();
        var discountAmount = (sum/100)*discount;
        return sum - discountAmount;
        }
        return sum;

    }

    public void isYourPassword(String pass) throws CustomException {
        if(pass.equals(this.password))
            return;
        throw new CustomException("passWordNotFound");
    }

    public void removeFromUserBuyList(String commodityId) throws CustomException {
        CommodityManager.getInstance().getElementById(commodityId).cancelBuying();
        this.userBuyList.remove(commodityId);
    }

    public void addCredit(int credit){

        this.credit +=credit;
    }

    public boolean buy() throws CustomException {
        var sum = this.getCurrentBuyListPrice();
        if (sum > this.credit)
            return false;
        else {
            userPurchasedList.addAll(userBuyList);
            userBuyList.clear();
            this.credit -= sum;
            if(!discountCode.equals("")) {
                this.discountCodeUsed.add(discountCode);
                discountCode = "";
            }
            return true;
        }
    }

    public void setDiscount(String discountCode) throws CustomException {

        if(!DiscountManager.getInstance().isIdValid(discountCode))
            throw new CustomException("discount not found.");

        if(!discountCodeUsed.contains(discountCode))
            this.discountCode = discountCode;
    }

    public boolean isYourEmail(String email) {
        return Objects.equals(this.email, email);
    }

}
