package Baloot.repository;

import Baloot.Exeption.CustomException;
import Baloot.model.Commodity;
import Baloot.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepo extends Repo<User,String> {
    private static final Object BUYLIST_TABLE ="BuyList" ;
    private static final Object RATE_TABLE="RateTable";
    private static UserRepo instance = null;
    public static final String USER_TABLE = "User";
    public static User loggedInUser = null;

    public static UserRepo getInstance() {
        if (instance == null) {
            instance = new UserRepo();
        }
        return instance;
    }

    public static int getQuantityOfCommodity(Commodity commodity) throws SQLException {
        String sql=String.format(
                "SELECT b.quantity\n"
                +"FROM %s c, %s b\n"
                +"WHERE c.id=b.commodityId AND b.userId= ?"
                ,CommodityRepo.COMMODITY_TABLE, BUYLIST_TABLE);
        return instance.executeUpdate(sql, List.of(loggedInUser.getUsername()));
    }
    public static int getUserRate(Commodity commodity)throws SQLException{
        String sql=String.format(
                "SELECT r.rate\n"
                        +"FROM %s c, %s r\n"
                        +"WHERE c.id=r.commodityId AND r.userId= ?"
                ,CommodityRepo.COMMODITY_TABLE, RATE_TABLE);
        return instance.executeUpdate(sql, List.of(loggedInUser.getUsername()));
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
