package Baloot.repository;

import Baloot.Exeption.CustomException;
import Baloot.model.Provider;

public class ProviderRepo extends Repo<Provider> {
    public static final String PROVIDER_TABLE = "Provider";
    private static ProviderRepo instance;

    private ProviderRepo() {

    }

    public static ProviderRepo getInstance() {
        if (instance == null)
            instance = new ProviderRepo();
        return instance;
    }

    private void initProviderTable() {
        this.initTable(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %S(id INTEGER" +
                                "\nname VARCHAR(225)," +
                                "\nregistryDate VARCHAR(225)," +
                                "\nimgUrl VARCHAR(225)," +
                                "\nbirthDate VARCHAR(225)," +
                                "\nPRIMARY KEY(id));",
                        PROVIDER_TABLE
                )
        );
    }

    @Override
    public void addElement(Provider newObject) throws CustomException {
        var objectId = String.valueOf(newObject.getId());

        if (isIdValid(objectId)) {
            throw new CustomException("Object exist");
        }
        this.objectMap.put(objectId, newObject);
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
