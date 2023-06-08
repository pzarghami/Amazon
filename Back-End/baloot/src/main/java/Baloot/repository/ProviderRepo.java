package Baloot.repository;

import Baloot.Exeption.CustomException;
import Baloot.model.Commodity;
import Baloot.model.Provider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProviderRepo extends Repo<Provider, Integer> {
    public static final String PROVIDER_TABLE = "Provider";
    private static ProviderRepo instance;

    private ProviderRepo() {
        initActorTable();
    }

    public static ProviderRepo getInstance() {
        if (instance == null)
            instance = new ProviderRepo();
        return instance;
    }

    private void initActorTable() {
        this.initTable(
                String.format(
                        """
                                CREATE TABLE IF NOT EXISTS %s(id INTEGER,
                                name VARCHAR(225),
                                registryDate VARCHAR(225),
                                imgUrl VARCHAR(225),
                                PRIMARY KEY(id));""",
                        PROVIDER_TABLE));
    }

    @Override
    protected String getAddElementStatement() {
        return String.format("INSERT IGNORE INTO %s (id, name, registryDate, imgUrl) VALUES (?,?,?,?);",
                PROVIDER_TABLE);
    }


    @Override
    public void addElement(Provider newObject) throws CustomException, SQLException {
        var dbTuple = newObject.getDBTuple();
        executeUpdate(getAddElementStatement(), List.of(dbTuple.get("id"), dbTuple.get("name"), dbTuple.get("registryDate"), dbTuple.get("imgUrl")));
    }

    @Override
    protected String getGetElementByIdStatement() {
        return String.format("SELECT* FROM %s actor WHERE actor.id = ?;", PROVIDER_TABLE);
    }

    @Override
    protected void fillGetElementByIdValues(PreparedStatement st, Integer id) throws SQLException {
        try {
            st.setString(1, String.valueOf(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected String getGetAllElementsStatement() {
        return String.format("SELECT * FROM %s;", PROVIDER_TABLE);
    }

    @Override
    protected Provider convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new Provider(rs.getInt("id"), rs.getString("name"), rs.getString("registryDate"), rs.getString("imgUrl"));
    }

    @Override
    protected ArrayList<Provider> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
        ArrayList<Provider> providers = new ArrayList<>();
        try {
            while (rs.next()) {
                providers.add(this.convertResultSetToDomainModel(rs));
            }
            return providers;
        } catch (SQLException e) {
            e.printStackTrace();
            return providers;
        }
    }

    public ArrayList<Commodity> getCommoditiesOfProvider(Integer id) {
        var commodityIds = new ArrayList<Integer>();
        var commodities = new ArrayList<Commodity>();
        try {
            Connection con = ConnectionPool.getConnection();
            PreparedStatement st = con.prepareStatement(String.format("""
                    Select c.id
                    FROM Commodity c
                    where providerId = %s
                    """,id.toString()));
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                commodityIds.add(rs.getInt("id"));
            }
            commodities = (ArrayList<Commodity>) CommodityRepo.getInstance().getElementsById(commodityIds);
        } catch (SQLException | CustomException e) {
            e.printStackTrace();

        }
        return commodities;
    }

//    @Override
//    public void updateElement(Provider newObject) throws CustomException {
//        var objectId = String.valueOf(newObject.getId());
//        if (!isIdValid(objectId)) {
//            throw new CustomException("Object Not found");
//        }
//        objectMap.put(objectId, newObject);
//    }
}
