package Baloot.domain;

import Baloot.model.DTO.CommodityDTO;
import Baloot.model.DTO.UserDTO;
import Baloot.model.User;
import Baloot.repository.CommodityRepo;
import Baloot.repository.DiscountRepo;
import Baloot.repository.UserRepo;
import Baloot.Exeption.CustomException;

public class UserDomainManager {
    private static UserDomainManager instance;

    public static UserDomainManager getInstance() {
        if (instance == null) {
            instance = new UserDomainManager();
        }
        return instance;
    }

    public UserDTO getUserDTO() throws CustomException {
        return UserRepo.loggedInUser.getDTO();
    }

    public void loginUser(String username, String Password) throws CustomException {
        var user = UserRepo.getInstance().getElementById(username);
        if (!user.checkPassword(Password)) {
            throw new CustomException("password is wrong");
        }
        UserRepo.getInstance().loginUser(user);
    }

    public void logoutUser() {
        UserRepo.getInstance().logoutUser();
    }

    public void registerUser(String username, String password, String email, String birthdate, String address) throws CustomException {
        var user = new User(username, password, email, birthdate, address, 0);
        UserRepo.getInstance().addElement(user);
        // UserRepo.getInstance().loginUser(user);
    }

    public CommodityDTO addToBuyList(int commodityId) throws CustomException {
        var commodity = CommodityRepo.getInstance().getElementById(String.valueOf(commodityId));
        return UserRepo.loggedInUser.addToBuyList(commodity);
    }

    public CommodityDTO removeFromBuyList(int commodityId) throws CustomException {
        var commodity = CommodityRepo.getInstance().getElementById(String.valueOf(commodityId));
        return UserRepo.loggedInUser.removeFromBuyList(commodity);
    }

    public void addCredit(int amount) {
        UserRepo.loggedInUser.addCredit(amount);
    }

    public float getPrice() {
        return UserRepo.loggedInUser.getBuyListPrice();
    }

    public float setDiscount(String discountCode) throws CustomException {
        var discount = DiscountRepo.getInstance().getElementById(discountCode);
        UserRepo.loggedInUser.setDiscount(discount);
        return UserRepo.loggedInUser.getBuyListPrice();
    }

    public void removeDiscount() {
        UserRepo.loggedInUser.removeDiscount();
    }

    public UserDTO finalizeThePurchase() throws CustomException {
        UserRepo.loggedInUser.finalizeThePurchase();
        return UserRepo.loggedInUser.getDTO();
    }
}
