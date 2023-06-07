package Baloot.repository;

import Baloot.Exeption.CustomException;
import Baloot.model.Comment;
import Baloot.model.Commodity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import Baloot.model.DTO.CommodityBriefDTO;
import Baloot.model.Provider;
import Baloot.model.User;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.*;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

public class CommodityRepo extends Repo<Commodity, Integer> {
    private static CommodityRepo instance = null;
    public static final String COMMODITY_TABLE = "Commodity";
    public static final String CATEGORY_TABLE = "Category";
    public static final String RATE_TABLE = "CommodityRate";

    public CommodityRepo() {
        this.initCommodityTable();
        this.initCategoriesTable();
        System.out.println("HELLO");
        this.initCommodityRateTable();
        System.out.println("FUCK");
        this.notFoundException = new CustomException("CommodityNotFound");
    }

//
//    public static void updateAverageRate(Commodity commodity) throws SQLException {
//
//        var dbOutput=instance.executeQuery(String.format("""
//                SELECT AVG(R.rate) AS newAverage
//                FROM %s R
//                WHERE R.commodityId=?
//                """,RATE_TABLE),List.of(commodity.getId()));
//        var newAverage=dbOutput.getFirst().getString("newAverage");
//        instance.executeUpdate(String.format("""
//                UPDATE %s
//                SET averageRate=?
//                WHERE id=?
//                """,COMMODITY_TABLE),List.of(newAverage, commodity.getId()));
//
//
//    }


    public static void updateRateTable(String commodityId, User ratingUser, int rate) throws SQLException {
        String sql = String.format(
                "INSERT INTO %s(commodityId, userId, rate)\n" +
                        "VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE\n" +
                        "rate=?;", RATE_TABLE);
        instance.executeUpdate(sql, List.of(commodityId, ratingUser.getUsername(), String.valueOf(rate), String.valueOf(rate)));


    }

    @Override
    protected String getGetElementByIdStatement() {
        return String.format("SELECT * FROM %s c WHERE c.id = ?;", COMMODITY_TABLE);
    }

    private void initCommodityTable() {
        this.initTable(String.format(
                "CREATE TABLE IF NOT EXISTS %s\n" +
                        "(\n" +
                        "    id          INTEGER,\n" +
                        "    name        VARCHAR(255),\n" +
                        "    providerId  VARCHAR(255),\n" +
                        "    price       FLOAT,\n" +
                        "    inStock     INTEGER,\n" +
                        "    imgUrl      VARCHAR(255),\n" +
                        "    averageRate DOUBLE,\n" +
                        "    PRIMARY KEY (id),\n" +
                        "    FOREIGN KEY (providerId) REFERENCES " + ProviderRepo.PROVIDER_TABLE+" ON DELETE CASCADE \n" +
                        ");",
                COMMODITY_TABLE
        ));
    }

    private void initCategoriesTable() {
        this.initTable(
                String.format(
                        """
                                CREATE TABLE IF NOT EXISTS %s
                                (
                                    category VARCHAR(225),
                                    commodityId INTEGER ,
                                    FOREIGN KEY (commodityId) REFERENCES  Commodity  (id) ON DELETE CASCADE,
                                    PRIMARY KEY(commodityId, category)

                                );""",
                        CATEGORY_TABLE
                )
        );
    }

    private void initCommodityRateTable() {
        this.initTable(String.format(
                "CREATE TABLE IF NOT EXISTS %s(\n" +
                        "    userId INTEGER,\n" +
                        "    commodityId INTEGER,\n" +
                        "    rate INTEGER,\n" +
                        "    FOREIGN KEY (userId) REFERENCES " + UserRepo.USER_TABLE+" (id) ON DELETE CASCADE ,\n" +
                        "    FOREIGN KEY (commodityId) REFERENCES " + COMMODITY_TABLE+" (id) ON DELETE CASCADE,\n" +
                        "    PRIMARY KEY (commodityId,userId)\n" +
                        ");"
        ,RATE_TABLE));
    }

    public static CommodityRepo getInstance() {
        if (instance == null) {
            instance = new CommodityRepo();
        }
        return instance;
    }

    public static ArrayList<String> sortByValue(HashMap<String, Double> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Double>> list =
                new LinkedList<Map.Entry<String, Double>>(hm.entrySet());
        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        ArrayList<String> temp = new ArrayList<>();
        for (Map.Entry<String, Double> aa : list) {
            temp.add(aa.getKey());
        }
        return temp;
    }
    @Override
    protected String getAddElementStatement() {
        return String.format("INSERT IGNORE INTO %s\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?);", COMMODITY_TABLE);
    }
    @Override
    public void addElement(Commodity newObject) throws CustomException, SQLException {
        var tupleMap = newObject.getDBTuple();
        executeUpdate(getAddElementStatement(), List.of(
                tupleMap.get("id"),
                tupleMap.get("name"),
                tupleMap.get("provider"),
                tupleMap.get("price"),
                tupleMap.get("inStock"),
                tupleMap.get("imgUrl"),
                tupleMap.get("averageRate")
        ));
        addCommodityCategories(Integer.valueOf(newObject.getId()), newObject.getCategories());

    }

    @Override
    protected void fillGetElementByIdValues(PreparedStatement st, Integer id) throws SQLException {

    }

    private void addCommodityCategories(Integer commodityId, ArrayList<String> categories) throws SQLException {
        var sql = String.format(
                "INSERT IGNORE INTO %s(commodityId, category)\nVALUES (?,?);", CATEGORY_TABLE
        );
        Connection con = ConnectionPool.getConnection();
        PreparedStatement st = con.prepareStatement(sql);
        for(var category : categories) {
            fillValues(st, List.of(commodityId.toString(), category));
            st.executeUpdate();
        }
        st.close();
        con.close();
    }
    @Override
    public void updateElement(Commodity newObject) throws CustomException {
        var objectId = String.valueOf(newObject.getId());
        if (!isIdValid(objectId)) {
            throw new CustomException("Object Not found");
        }
        objectMap.put(objectId, newObject);

    }
    @Override
    protected String getGetAllElementsStatement() {
        return String.format("SELECT * FROM %s;", COMMODITY_TABLE);
    }

    @Override
    protected Commodity convertResultSetToDomainModel(ResultSet rs) throws SQLException {

        return new Commodity(
                rs.getString("id"),
                rs.getString("name"),
                getProviderFromDB(rs.getString("providerId")),
                rs.getFloat("price"),
                getCategoriesFromDB(rs.getInt("id")),
                rs.getInt("inStock"),
                rs.getString("imgUrl")
        );
    }
    public HashMap<String, Integer> getUserRateMap(Integer commodityId) throws SQLException {
        var hashMap = new HashMap<String, Integer>();
        String sql = String.format("SELECT userId, rate FROM %s WHERE commodityId=?;", RATE_TABLE);
        var dbOutput = executeQuery(sql, List.of(commodityId.toString()));
        var res = dbOutput.getFirst();
        while (res.next()) {
            hashMap.put(res.getString("userId"), res.getInt("rate"));
        }
        finishWithResultSet(dbOutput.getSecond());
        return hashMap;
    }

    private ArrayList<String > getCategoriesFromDB(Integer id) throws SQLException {
        ArrayList<String> categories=new ArrayList<>();
        String sqlSelect= String.format(
                "SELECT C.category\n" +
                "FROM %s C\n" +
                "WHERE C.commodityId== ?",CATEGORY_TABLE);
        var dbOutput= executeQuery(sqlSelect, List.of(id.toString()));
        var rs=dbOutput.getFirst();
        while(rs.next()){
            categories.add(rs.getString("category"));
        }
        finishWithResultSet(dbOutput.getSecond());
        return categories;

    }

    private Provider getProviderFromDB(String providerId) throws SQLException {
        String sql=String.format(
                "SELECT *\n" +
                "FROM %s P \n" +
                "WHERE p.id= ?",ProviderRepo.PROVIDER_TABLE
        );
        var dbOutput=executeQuery(sql,List.of(providerId));
        var rs=dbOutput.getFirst();
        var provider=new Provider(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("registryDate"),
                rs.getString("imgUrl")

        );
        finishWithResultSet(dbOutput.getSecond());
        return provider;
    }

    @Override
    protected ArrayList<Commodity> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
        ArrayList<Commodity> commodities=new ArrayList<>();
        while(rs.next()){
            commodities.add(convertResultSetToDomainModel(rs));
        }
        return commodities;
    }


    public ArrayList<CommodityBriefDTO> calculateSuggestedProducts(String commodityId) throws CustomException, SQLException {
        var commodity = getElementById(Integer.valueOf(commodityId));
        var commodityCategory = commodity.getCategories();
        HashMap<String, Double> suggestedCommoditiesId = new HashMap<>();
        List<Commodity> commodities= getAllElements();
        for(var comm : commodities){
            if(comm.getId().equals(commodityId))
                continue;
            if(comm.isYourCategory(commodityCategory))
                suggestedCommoditiesId.put(comm.getId(), 11 + comm.getAverageRating());
            else
                suggestedCommoditiesId.put(comm.getId(),comm.getAverageRating());
        }
        var commodityIdWithTopScores = sortByValue(suggestedCommoditiesId);
        ArrayList<Integer> fiveCommodityIdWithTopScores = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            fiveCommodityIdWithTopScores.add(Integer.valueOf(commodityIdWithTopScores.get(i)));
        }
        var commoditiesTop = getElementsById(fiveCommodityIdWithTopScores);
        var commodityBriefDTO = new ArrayList<CommodityBriefDTO>();
        commoditiesTop.forEach(commodity1 -> commodityBriefDTO.add(commodity1.getBriefDTO(0)));
        return commodityBriefDTO;

    }
    public  ArrayList<Commodity> getFilteredElementsByName(String filterValue) throws SQLException {
        String sql = String.format("""
                        SELECT *
                        FROM %s
                        WHERE name LIKE '%%%s%%';
                        """, COMMODITY_TABLE, filterValue);
        var dbOutput=executeQuery(sql,List.of());
        var rs=dbOutput.getFirst();
        return convertResultSetToDomainModelList(rs);


    }

    public  ArrayList<Commodity> getFilteredElementsByCategory(String filterValue) throws SQLException {

        String sql = String.format("""
                                SELECT c.*
                                FROM %s c, %s cat
                                WHERE name LIKE '%%%s%%' AND c.id=cat.commodityId;
                                """, COMMODITY_TABLE,CATEGORY_TABLE, filterValue);
        var dbOutput=executeQuery(sql,List.of());
        var rs=dbOutput.getFirst();
        return convertResultSetToDomainModelList(rs);

    }

    public  ArrayList<Commodity> getFilteredElementsByProvider(String filterValue) throws SQLException {
        String sql = String.format("""
                SELECT c.*
                FROM %s p, %s c
                WHERE   p.name LIKE '%%%s%%'AND p.id = c.providerId ;
                        """,
                ProviderRepo.PROVIDER_TABLE,COMMODITY_TABLE, filterValue);
        var dbOutput=executeQuery(sql,List.of());
        var rs=dbOutput.getFirst();
        return convertResultSetToDomainModelList(rs);
    }



}
