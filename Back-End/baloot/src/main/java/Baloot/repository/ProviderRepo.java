package Baloot.repository;

import Baloot.Exeption.CustomException;
import Baloot.model.Provider;

public class ProviderRepo extends Repo<Provider> {
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
