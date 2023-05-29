package Baloot.model;

import Baloot.Exeption.CustomException;
import Baloot.model.DTO.CommodityBriefDTO;
import Baloot.model.DTO.CommodityDTO;
import Baloot.model.DTO.UserDTO;

import java.sql.SQLException;
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
    private Map<Commodity, Integer> buyList;
    private Map<Commodity, Integer> userPurchasedList;
    private ArrayList<Discount> discountCodeUsed;

    public User(String username, String password, String email, String birthDate, String address, int credit) {
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

    public String getUsername() {
        return username;
    }

    public CommodityDTO addToBuyList(Commodity commodity) throws CustomException, SQLException {
        if(commodity.getInStock()<1)
            throw new CustomException("there is not enough commodity");
        commodity.decreaseInStock();
        if (buyList.containsKey(commodity)) {
            buyList.put(commodity, buyList.get(commodity) + 1);
        } else {
            buyList.put(commodity, 1);
        }
        return commodity.getDTO(buyList.get(commodity));
    }

    public CommodityDTO removeFromBuyList(Commodity commodity) throws CustomException, SQLException {
        int quantity;
        if (!buyList.containsKey(commodity))
            throw new CustomException("don't exist.");
        if (buyList.get(commodity) > 1) {
            buyList.put(commodity, buyList.get(commodity) - 1);
            quantity = buyList.get(commodity);
        } else {
            buyList.remove(commodity);
            quantity = 0;
        }
        commodity.increaseInStock();
        return commodity.getDTO(quantity);
    }

    public int getQuantityOfCommodity(Commodity commodity) {
        return buyList.getOrDefault(commodity, 0);
    }

    public void finalizeThePurchase() throws CustomException {
        var buyListPrice = getBuyListPrice();
        if (buyListPrice > this.credit)
            throw new CustomException("credit is not enough");
        userPurchasedList.putAll(buyList);
        buyList.clear();
        if (discount != null) {
            this.discountCodeUsed.add(discount);
            this.discount = null;
        }
    }

    public int getBuyListSize() {
        return buyList.size();
    }

    public void addCredit(int amount) {
        this.credit += amount;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void setDiscount(Discount discount) throws CustomException {
        if (discountCodeUsed.contains(discount))
            throw new CustomException("discount is used");
        this.discount = discount;
    }

    public void removeDiscount() {
        discount = null;
    }

    public float getBuyListPrice() {
        float sumPrice = 0;
        for (Commodity commodity : buyList.keySet()) {
            sumPrice += commodity.getPrice();
        }
        if (discount == null)
            return sumPrice;
        else {
            var discountAmount = (sumPrice / 100) * discount.getDiscountAmount();
            return sumPrice - discountAmount;
        }
    }

    public UserDTO getDTO() throws CustomException, SQLException {
        var DTO = new UserDTO();
        DTO.setUsername(username);
        DTO.setEmail(email);
        DTO.setBirthDate(birthDate);
        DTO.setAddress(address);
        DTO.setCredit(credit);

        var buyListDTO = new ArrayList<CommodityDTO>();
        for (Map.Entry<Commodity, Integer> entry : buyList.entrySet()) {
            buyListDTO.add(entry.getKey().getDTO(entry.getValue()));
        }
        DTO.setBuyList(buyListDTO);

        var purchasedListDTO = new ArrayList<CommodityDTO>();
        for (Map.Entry<Commodity, Integer> entry : userPurchasedList.entrySet()) {
            purchasedListDTO.add(entry.getKey().getDTO(entry.getValue()));
        }
        DTO.setUserPurchasedList(purchasedListDTO);

        return DTO;
    }
}
