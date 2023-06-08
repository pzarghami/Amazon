package Baloot.repository;

import Baloot.Exeption.CustomException;
import Baloot.model.Discount;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiscountRepo extends Repo<Discount, String> {
    private static DiscountRepo instance = null;
    public static final String DISCOUNT_TABLE = "Discount";

    public static DiscountRepo getInstance() {
        if (instance == null) {
            instance = new DiscountRepo();
        }
        return instance;
    }

    private DiscountRepo() {
        initDiscountTable();
    }

    private void initDiscountTable() {
        initTable(
                String.format(
                        """
                                CREATE TABLE IF NOT EXISTS %s(code VARCHAR(255),
                                amount INTEGER,
                                PRIMARY KEY(code));""",
                        DISCOUNT_TABLE
                )
        );
    }

    @Override
    protected String getGetElementByIdStatement() {
        return String.format("SELECT * FROM %s a WHERE a.code = ?;", DISCOUNT_TABLE);
    }

    @Override
    public void addElement(Discount newObject) throws CustomException, SQLException {
        var tupleMap = newObject.getDBTuple();
        executeUpdate(getAddElementStatement(), List.of(
                tupleMap.get("code"),
                tupleMap.get("amount")));
    }

    @Override
    protected void fillGetElementByIdValues(PreparedStatement st, String id) throws SQLException {
        st.setString(1, id);
    }


    @Override
    protected String getAddElementStatement() {
        return String.format("INSERT IGNORE INTO %s\n" +
                "VALUES (?, ?);", DISCOUNT_TABLE);
    }

    @Override
    protected String getGetAllElementsStatement() {
        return String.format("SELECT * FROM %s;", DISCOUNT_TABLE);
    }

    @Override
    protected Discount convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new Discount(rs.getString("code"),rs.getInt("amount"));
    }

    @Override
    protected ArrayList<Discount> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
        ArrayList<Discount> discounts = new ArrayList<>();
        while (rs.next()){
            discounts.add(this.convertResultSetToDomainModel(rs));
        }
        return discounts;
    }

//    @Override
//    public void updateElement(Discount newObject) throws CustomException {
//        var objectId = String.valueOf(newObject.getDiscountCode());
//        if (!isIdValid(objectId)) {
//            throw new CustomException("Object Not found");
//        }
//        objectMap.put(objectId, newObject);
//    }
}
