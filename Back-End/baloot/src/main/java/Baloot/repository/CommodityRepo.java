package Baloot.repository;

import Baloot.Exeption.CustomException;
import Baloot.model.Commodity;

public class CommodityRepo extends Repo<Commodity> {
    private static CommodityRepo instance = null;

    public static CommodityRepo getInstance(){
        if(instance == null){
            instance = new CommodityRepo();
        }
        return instance;
    }

    @Override
    public void addElement(Commodity newObject) throws CustomException {
        var objectId = String.valueOf(newObject.getId());

        if (isIdValid(objectId)) {
            throw new CustomException("Object exist");
        }
        this.objectMap.put(objectId,newObject);

    }

    @Override
    public void updateElement(Commodity newObject) throws CustomException {
        var objectId = String.valueOf(newObject.getId());
        if (!isIdValid(objectId)) {
            throw new CustomException("Object Not found");
        }
        objectMap.put(objectId, newObject);

    }

}
