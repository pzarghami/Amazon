package Baloot.repository;

import Baloot.Exeption.CustomException;
import Baloot.model.Commodity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import Baloot.model.DTO.CommodityBriefDTO;
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
                        "    FOREIGN KEY (providerId) REFERENCES" + ProviderRepo.PROVIDER_TABLE+"ON DELETE CASCADE \n" +
                        ");",
                COMMODITY_TABLE
        ));
    }

    private void initCategoriesTable() {
        this.initTable(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s\n" +
                                "(\n" +
                                "    category VARCHAR(225),\n" +
                                "    commodityId INTEGER ,\n" +
                                "    FOREIGN KEY (commodityId) REFERENCES  Commodity  (id) ON DELETE CASCADE,\n" +
                                "    PRIMARY KEY(commodityId, category)\n" +
                                "\n" +
                                ");",
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

    public ArrayList<CommodityBriefDTO> calculateSuggestedProducts(String commodityId) throws CustomException {
        var commodity = getElementById(commodityId);
        var commodityCategory = commodity.getCategories();
        HashMap<String, Double> suggestedCommoditiesId = new HashMap<>();
        for (var pair : objectMap.entrySet()) {
            if (String.valueOf(pair.getValue().getId()).equals(commodityId))
                continue;
            if (pair.getValue().isYourCategory(commodityCategory))
                suggestedCommoditiesId.put(pair.getKey(), 11 + pair.getValue().getAverageRating());
            else
                suggestedCommoditiesId.put(pair.getKey(), pair.getValue().getAverageRating());
        }
        var commodityIdWithTopScores = sortByValue(suggestedCommoditiesId);
        ArrayList<String> fiveCommodityIdWithTopScores = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            fiveCommodityIdWithTopScores.add(commodityIdWithTopScores.get(i));
        }
        var commodities = getElementsById(fiveCommodityIdWithTopScores);
        var commodityBriefDTO = new ArrayList<CommodityBriefDTO>();
        commodities.forEach(commodity1 -> commodityBriefDTO.add(commodity1.getBriefDTO(0)));
        return commodityBriefDTO;

    }

}
