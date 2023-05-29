package Baloot.repository;

import Baloot.Exeption.CustomException;
import Baloot.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserRepo extends Repo<User,String> {
    private static UserRepo instance = null;
    public static final String USER_TABLE = "User";
    public static User loggedInUser = null;

    public static UserRepo getInstance() {
        if (instance == null) {
            instance = new UserRepo();
        }
        return instance;
    }

    @Override
    protected String getGetElementByIdStatement() {
        return null;
    }

    @Override
    public void addElement(User newObject) throws CustomException {
        var objectId = newObject.getUsername();
        if (isIdValid(objectId)) {
            throw new CustomException("Object exist");
        }
        this.objectMap.put(objectId,newObject);
    }

    @Override
    protected void fillGetElementByIdValues(PreparedStatement st, String id) throws SQLException {

    }

    @Override
    protected String getAddElementStatement() {
        return null;
    }

    @Override
    protected String getGetAllElementsStatement() {
        return null;
    }

    @Override
    protected User convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    protected ArrayList<User> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
        return null;
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
