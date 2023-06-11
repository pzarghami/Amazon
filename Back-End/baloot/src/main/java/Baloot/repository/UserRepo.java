package Baloot.repository;

import Baloot.Exeption.CustomException;
import Baloot.model.Commodity;
import Baloot.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepo extends Repo<User, String> {
    private static final String BUYLIST_TABLE = "BuyList";
    public static final String USER_TABLE = "User";
    private static UserRepo instance = null;
    public static User loggedInUser = null;

    public static UserRepo getInstance() {
        if (instance == null) {
            instance = new UserRepo();
        }
        return instance;
    }

    private UserRepo() {
        initUserTable();
        initBuyListTable();
    }

    private void initUserTable() {
        initTable(
                String.format(
                        """
                                CREATE TABLE IF NOT EXISTS %s(username VARCHAR(255),
                                password VARCHAR(255),
                                email VARCHAR(255),
                                birthDate VARCHAR(225),
                                address VARCHAR(255),
                                credit INTEGER,
                                PRIMARY KEY(username));""",
                        USER_TABLE
                )
        );
    }
    private void initBuyListTable(){
        initTable(
                String.format(
                        """
                                CREATE TABLE IF NOT EXISTS %s(userId VARCHAR(255),
                                commodityID INTEGER,
                                PRIMARY KEY(userId,commodityID),
                                FOREIGN KEY (userId) REFERENCES %s (username),
                                FOREIGN KEY (commodityID) REFERENCES %s (id)
                                );""",
                        BUYLIST_TABLE,USER_TABLE,CommodityRepo.COMMODITY_TABLE
                )
        );
    }

    public static int getQuantityOfCommodity(Commodity commodity) throws SQLException {
        //when buyList is done.
//        String sql = String.format(
//                """
//                        SELECT b.quantity
//                        FROM %s c, %s b
//                        WHERE c.id=b.commodityId AND b.userId= ?"""
//                , CommodityRepo.COMMODITY_TABLE, BUYLIST_TABLE);
//        return instance.executeUpdate(sql, List.of(loggedInUser.getUsername()));
        return 0;
    }

    @Override
    protected String getGetElementByIdStatement() {
        return String.format("SELECT * FROM %s a WHERE a.username = ?;", USER_TABLE);
    }

    @Override
    public void addElement(User newObject) throws CustomException, SQLException {
        var tupleMap = newObject.getDBTuple();
        executeUpdate(getAddElementStatement(), List.of(
                tupleMap.get("username"),
                tupleMap.get("password"),
                tupleMap.get("email"),
                tupleMap.get("birthDate"),
                tupleMap.get("address"),
                tupleMap.get("credit")));
    }

    @Override
    protected void fillGetElementByIdValues(PreparedStatement st, String id) throws SQLException {
        st.setString(1, id);
    }

    @Override
    protected String getAddElementStatement() {
        return String.format("INSERT IGNORE INTO %s\n" +
                "VALUES (?, ?, ?, ?, ?, ?);", USER_TABLE);
    }

    @Override
    protected String getGetAllElementsStatement() {
        return String.format("SELECT * FROM %s;", USER_TABLE);
    }

    @Override
    protected User convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new User(rs.getString("username"), rs.getString("password"), rs.getString("email"),
                rs.getString("birthDate"), rs.getString("address"), rs.getInt("credit"));
    }

    @Override
    protected ArrayList<User> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        while (rs.next()) {
            users.add(this.convertResultSetToDomainModel(rs));
        }
        return users;
    }

//    @Override
//    public void updateElement(User newObject) throws CustomException {
//        var objectId = newObject.getUsername();
//        if (!isIdValid(objectId)) {
//            throw new CustomException("Object Not found");
//        }
//        objectMap.put(objectId, newObject);
//    }

    public void addCredit(String username, Integer amount){
        var st = String.format("UPDATE %s SET credit = credit + ?" +
                "WHERE username = ?",USER_TABLE);
        try {
            executeUpdate(st, List.of(username, amount.toString()));
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void loginUser(User user) {
        loggedInUser = user;
    }

    public void logoutUser() {
        loggedInUser = null;
    }

}
