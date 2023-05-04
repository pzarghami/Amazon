package Baloot.repository;

import Baloot.Exeption.CustomException;
import Baloot.model.User;

public class UserRepo extends Repo<User> {
    private static UserRepo instance = null;

    public static UserRepo getInstance() {
        if (instance == null) {
            instance = new UserRepo();
        }
        return instance;
    }

    @Override
    public void addElement(User newObject) throws CustomException {
        var objectId = newObject.getUsername();
        if (isIdValid(objectId)) {
            throw new CustomException("Object exist");
        }
        this.objectMap.put(objectId,newObject);
        return;
    }

    @Override
    public void updateElement(User newObject) throws CustomException {
        var objectId = newObject.getUsername();
        if (!isIdValid(objectId)) {
            throw new CustomException("Object Not found");
        }
        objectMap.put(objectId, newObject);
    }

    public void loginUser(User user) {
        loggedInUser = user;
    }

    public void logoutUser() {
        loggedInUser = null;
    }
}
