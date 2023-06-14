package Baloot.repository;

import Baloot.Exeption.CustomException;
import Baloot.model.Commodity;
import Baloot.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepo extends Repo<User, String> {
    private static final String BUYLIST_TABLE = "BuyList";
    private static final String PURCHASED_TABLE = "PurchasedList";
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
        initPurchasedListTable();
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

    private void initBuyListTable() {
        initTable(
                String.format(
                        """
                                CREATE TABLE IF NOT EXISTS %s(userId VARCHAR(255),
                                commodityID INTEGER,
                                quantity INTEGER,
                                PRIMARY KEY(userId,commodityID),
                                FOREIGN KEY (userId) REFERENCES %s (username),
                                FOREIGN KEY (commodityID) REFERENCES %s (id)
                                );""",
                        BUYLIST_TABLE, USER_TABLE, CommodityRepo.COMMODITY_TABLE
                )
        );
    }

    private void initPurchasedListTable() {
        initTable(
                String.format(
                        """
                                CREATE TABLE IF NOT EXISTS %s(userId VARCHAR(255),
                                commodityID INTEGER,
                                quantity INTEGER,
                                PRIMARY KEY(userId,commodityID),
                                FOREIGN KEY (userId) REFERENCES %s (username),
                                FOREIGN KEY (commodityID) REFERENCES %s (id)
                                );""",
                        PURCHASED_TABLE, USER_TABLE, CommodityRepo.COMMODITY_TABLE
                )
        );
    }


    public int getQuantityOfCommodity(Commodity commodity) throws SQLException {
        var quantity = 0;
        try {
            String st = String.format("""
                    select quantity
                    FROM %s
                    WHERE userId = ? AND commodityID = ?;
                    """, BUYLIST_TABLE);
            var dbOutput = executeQuery(st, List.of(loggedInUser.getUsername(), commodity.getId()));
            var res = dbOutput.getFirst();
            while (res.next()) {
                quantity = res.getInt("quantity");
            }
            finishWithResultSet(dbOutput.getSecond());
        } catch (SQLException e) {
            e.printStackTrace();
            return quantity;
        }
        return quantity;
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

    public void addCredit(String username, Integer amount) {
        var st = String.format("UPDATE %s SET credit = credit + ?" +
                "WHERE username = ?", USER_TABLE);
        try {
            executeUpdate(st, List.of(amount.toString(),username));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int addToBuyList(Commodity commodity) {
        try {
            String st = String.format("""
                    SELECT inStock AS inStock
                    FROM %s c
                    WHERE c.id = ?;
                    """, CommodityRepo.COMMODITY_TABLE);

            var dbOutput = executeQuery(st, List.of(commodity.getId()));
            var res = dbOutput.getFirst();
            int quantity = 0;
            res.next();
            quantity = res.getInt("inStock");
            finishWithResultSet(dbOutput.getSecond());
            if (quantity > 0) {
                String st2 = String.format("""
                        UPDATE %s
                        SET inStock = inStock - 1
                        WHERE id = ?;
                        """, CommodityRepo.COMMODITY_TABLE);
                executeUpdate(st2, List.of(commodity.getId()));
                String st3 = String.format("""
                           INSERT INTO %s
                           VALUE (?,?,?) on duplicate key
                           update quantity = quantity + 1;
                        """, BUYLIST_TABLE);
                executeUpdate(st3, List.of(loggedInUser.getUsername(), commodity.getId(), "1"));
                String st4 = String.format("""
                        SELECT quantity AS quantity
                        FROM %s b
                        WHERE b.userId = ? and b.commodityId = ?;
                        """, BUYLIST_TABLE);
                dbOutput = executeQuery(st4, List.of(loggedInUser.getUsername(), commodity.getId()));
                res = dbOutput.getFirst();
                res.next();
                var temp = res.getInt("quantity");
                finishWithResultSet(dbOutput.getSecond());
                return temp;
            } else {
                throw new CustomException("there is not enough in stock");
            }

        } catch (SQLException | CustomException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int removeFromBuyList(Commodity commodity) {
        var quantity = 0;
        try {
            String st = String.format("""
                    SELECT quantity
                    FROM %s
                    WHERE userId = ? AND commodityID = ?;
                    """, BUYLIST_TABLE);
            var dbOutput = executeQuery(st, List.of(loggedInUser.getUsername(), commodity.getId()));
            var res = dbOutput.getFirst();
            while (res.next()) {
                quantity = res.getInt("quantity");
            }
            finishWithResultSet(dbOutput.getSecond());
            if (quantity > 0) {

                String st2 = String.format("""
                        UPDATE %s SET quantity = quantity - 1
                        WHERE userId = ? and commodityID = ?;
                        """, BUYLIST_TABLE);
                executeUpdate(st2, List.of(loggedInUser.getUsername(), commodity.getId()));
                String st3 = String.format("""
                        UPDATE %s SET inStock = inStock + 1
                        WHERE id = ?;
                        """, CommodityRepo.COMMODITY_TABLE);
                executeUpdate(st3, List.of(commodity.getId()));
                return quantity - 1;
            } else return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return quantity;
        }
    }

    public int getBuyListPrice() {
        try {
            String st = """
                    SELECT SUM(C.price* B.quantity) AS TOTAL
                    from Commodity C , BuyList B
                    where B.commodityID = C.id
                    """;
            var dbOutput = executeQuery(st, List.of());
            var res = dbOutput.getFirst();
            res.next();
            var price = res.getInt("TOTAL");
            finishWithResultSet(dbOutput.getSecond());
            return price;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getCredit(){
        try {
            String st = String.format("""
                    SELECT credit 
                    FROM %s U 
                    WHERE U.username = ?
                    """,USER_TABLE);
            var dbOutput = executeQuery(st,List.of(loggedInUser.getUsername()));
            var res = dbOutput.getFirst();
            res.next();
            var credit = res.getInt("credit");
            finishWithResultSet(dbOutput.getSecond());
            return credit;
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public void completeBuy(Integer price) {
        try {
            String st = """
                    insert into PurchasedList (userId, commodityID, quantity)
                    SELECT userId,commodityID,quantity
                    from BuyList on duplicate key update quantity = PurchasedList.quantity + BuyList.quantity;
                    """;

            executeUpdate(st, List.of());
            String st2 = "delete from BuyList;";
            executeUpdate(st2,List.of());
            String st3 = String.format("""
                    UPDATE %s SET credit = credit - ?
                    WHERE username = ?
                    """,USER_TABLE);
            executeUpdate(st3,List.of(price.toString(), loggedInUser.getUsername()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void loginUser(User user) {
        loggedInUser = user;
    }

    public void logoutUser() {
        loggedInUser = null;
    }

    public ArrayList<Commodity> getCommoditiesOfBuyList(boolean isForBuyList) {
        String table = BUYLIST_TABLE;
        var commodityIds = new ArrayList<Integer>();
        var commodities = new ArrayList<Commodity>();
        try {
            if (!isForBuyList) {
                table = PURCHASED_TABLE;
            }
            String st = String.format("""
                    Select b.commodityId
                    FROM %s b
                    where b.userId = ? AND b.quantity > 0
                    """, table);
            var dbOutput = executeQuery(st, List.of(loggedInUser.getUsername()));
            var rs = dbOutput.getFirst();
            while (rs.next()) {
                commodityIds.add(rs.getInt("commodityId"));
            }
            commodities = (ArrayList<Commodity>) CommodityRepo.getInstance().getElementsById(commodityIds);
            finishWithResultSet(dbOutput.getSecond());
        } catch (SQLException | CustomException e) {
            e.printStackTrace();
        }
        return commodities;
    }
}
