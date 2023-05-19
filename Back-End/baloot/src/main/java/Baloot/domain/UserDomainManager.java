package Baloot.domain;

import Baloot.model.User;
import Baloot.repository.UserRepo;
import Baloot.Exeption.CustomException;

public class UserDomainManager {
    private static UserDomainManager instance;
    public static UserDomainManager getInstance(){
        if (instance == null) {
            instance = new UserDomainManager();
        }
        return instance;
    }
    public void loginUser(String username, String Password) throws CustomException {
        var user = UserRepo.getInstance().getElementById(username);
        if(!user.checkPassword(Password)) {
            throw new CustomException("password is wrong");
        }
        UserRepo.getInstance().loginUser(user);
    }

    public void logoutUser(){
        UserRepo.getInstance().logoutUser();
    }

    public void registerUser(String username, String password, String email, String birthdate, String address) throws CustomException {
        var user = new User(username, password, email, birthdate, address, 0);
        UserRepo.getInstance().addElement(user);
        UserRepo.getInstance().loginUser(user);
    }
}
