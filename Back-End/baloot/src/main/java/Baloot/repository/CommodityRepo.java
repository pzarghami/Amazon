package Baloot.repository;

import Baloot.Exeption.CustomException;
import Baloot.model.Commodity;

import java.util.ArrayList;
import java.util.HashMap;

import Baloot.model.DTO.CommodityBriefDTO;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.*;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

public class CommodityRepo extends Repo<Commodity> {
    private static CommodityRepo instance = null;

    public static CommodityRepo getInstance() {
        if (instance == null) {
            instance = new CommodityRepo();
        }
        return instance;
    }

    public static ArrayList<String> sortByValue(HashMap<String, Double> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Double>> list =
                new LinkedList<Map.Entry<String, Double>>(hm.entrySet());
        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        ArrayList<String> temp = new ArrayList<>();
        for (Map.Entry<String, Double> aa : list) {
            temp.add(aa.getKey());
        }
        return temp;
    }

    @Override
    public void addElement(Commodity newObject) throws CustomException {
        var objectId = String.valueOf(newObject.getId());

        if (isIdValid(objectId)) {
            throw new CustomException("Object exist");
        }
        this.objectMap.put(objectId, newObject);
        newObject.getProvider().addCommodity(newObject);
    }

    @Override
    public void updateElement(Commodity newObject) throws CustomException {
        var objectId = String.valueOf(newObject.getId());
        if (!isIdValid(objectId)) {
            throw new CustomException("Object Not found");
        }
        objectMap.put(objectId, newObject);

    }

    public ArrayList<CommodityBriefDTO> calculateSuggestedProducts(String commodityId) throws CustomException {
        var commodity = getElementById(commodityId);
        var commodityCategory = commodity.getCategories();
        HashMap<String, Double> suggestedCommoditiesId = new HashMap<>();
        for (var pair : objectMap.entrySet()) {
            if (String.valueOf(pair.getValue().getId()).equals(commodityId))
                continue;
            if (pair.getValue().isYourCategory(commodityCategory))
                suggestedCommoditiesId.put(pair.getKey(), 11 + pair.getValue().getAverageRating());
            else
                suggestedCommoditiesId.put(pair.getKey(), pair.getValue().getAverageRating());
        }
        var commodityIdWithTopScores = sortByValue(suggestedCommoditiesId);
        ArrayList<String> fiveCommodityIdWithTopScores = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            fiveCommodityIdWithTopScores.add(commodityIdWithTopScores.get(i));
        }
        var commodities = getElementsById(fiveCommodityIdWithTopScores);
        var commodityBriefDTO = new ArrayList<CommodityBriefDTO>();
        commodities.forEach(commodity1 -> commodityBriefDTO.add(commodity1.getBriefDTO(0)));
        return commodityBriefDTO;

    }

}
