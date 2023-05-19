package Baloot.domain;

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
}
