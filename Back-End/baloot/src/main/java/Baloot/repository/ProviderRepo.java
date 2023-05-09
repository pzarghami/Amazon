package Baloot.repository;

import Baloot.Exeption.CustomException;
import Baloot.model.Provider;

public class ProviderRepo extends Repo<Provider> {
    private static ProviderRepo instance;
    public static ProviderRepo getInstance(){
        if(instance == null)
            instance = new ProviderRepo();
        return instance;
    }

    @Override
    public void addElement(Provider newObject) throws CustomException {
        var objectId = String.valueOf(newObject.getId());

        if (isIdValid(objectId)) {
            throw new CustomException("Object exist");
        }
        this.objectMap.put(objectId,newObject);
    }

    @Override
    public void updateElement(Provider newObject) throws CustomException {
        var objectId = String.valueOf(newObject.getId());
        if (!isIdValid(objectId)) {
            throw new CustomException("Object Not found");
        }
        objectMap.put(objectId, newObject);
    }
}
