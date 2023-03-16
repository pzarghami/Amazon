package ie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ie.exeption.CustomException;

public abstract class Manager <T> {
    protected Map<String,T> objectMap;

    public Manager () {
        objectMap = new HashMap<>();
    }

    public abstract String addElement(T newObject) throws CustomException;
    public abstract String updateElement(T newObject) throws CustomException;

    public T getElementById(String id) throws CustomException {
        if (!objectMap.containsKey(id)) {
            throw new CustomException("object not found."); // TODO: handle exception message properly
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
    public boolean isIdListValid(List<String> ids) {
        return objectMap.keySet().containsAll(ids);
    }
    public void removeElements(List<String> ids) {
        if (ids == null) {
            objectMap.clear();
        }
    }

}
