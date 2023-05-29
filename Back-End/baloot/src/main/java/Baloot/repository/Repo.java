package Baloot.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Baloot.Exeption.CustomException;
import Baloot.model.User;

public abstract class Repo<T> {
    static User loggedInUser;
    protected Map<String, T> objectMap;

    public Repo() {
        objectMap = new HashMap<>();
    }

    public abstract void addElement(T newObject) throws CustomException;

    public abstract void updateElement(T newObject) throws CustomException;

    public T getElementById(String id) throws CustomException {
        if (!objectMap.containsKey(id)) {
            throw new CustomException("Object not found");
        }
        return objectMap.get(id);
    }

    public List<T> getElementsById(List<String> ids) throws CustomException {
        if (ids == null) {
            return new ArrayList<T>(objectMap.values());
        }
        List<T> objects = new ArrayList<>();
        for (String id : ids) {
            objects.add(getElementById(id));
        }
        return objects;
    }

    public boolean isIdValid(String id) {
        return objectMap.containsKey(id);
    }

    protected void initTable(String createTableSql) {
        try {
            Connection con = ConnectionPool.getConnection();
            PreparedStatement createTableStatement = con.prepareStatement(createTableSql);
            createTableStatement.executeUpdate();
            createTableStatement.close();
            con.close();
        } catch (SQLException e) {
            //ignore
        }
    }

    public boolean isIdListValid(List<String> ids) {
        return objectMap.keySet().containsAll(ids);
    }

    public void removeElements(List<String> ids) {
        if (ids == null) {
            objectMap.clear();
        }
    }
}
