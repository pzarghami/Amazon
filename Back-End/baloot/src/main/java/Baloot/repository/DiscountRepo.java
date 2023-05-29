package Baloot.repository;

import Baloot.Exeption.CustomException;
import Baloot.model.Discount;

public class DiscountRepo extends Repo<Discount,Integer> {
    private static DiscountRepo instance = null;

    public static DiscountRepo getInstance(){
        if(instance == null){
            instance = new DiscountRepo();
        }
        return instance;
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
    protected String getAddElementStatement() {
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
