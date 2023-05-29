package Baloot.repository;

import Baloot.Exeption.CustomException;
import Baloot.model.Discount;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DiscountRepo extends Repo<Discount,String> {
    private static DiscountRepo instance = null;

    public static DiscountRepo getInstance(){
        if(instance == null){
            instance = new DiscountRepo();
        }
        return instance;
    }

    @Override
    protected String getGetElementByIdStatement() {
        return null;
    }

    @Override
    public void addElement(Discount newObject) throws CustomException {
        var objectId = String.valueOf(newObject.getDiscountCode());

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
    protected Discount convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    protected ArrayList<Discount> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    public void updateElement(Discount newObject) throws CustomException {
        var objectId = String.valueOf(newObject.getDiscountCode());
        if (!isIdValid(objectId)) {
            throw new CustomException("Object Not found");
        }
        objectMap.put(objectId, newObject);
    }
}
