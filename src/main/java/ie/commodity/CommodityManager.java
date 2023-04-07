package ie.commodity;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ie.*;
import ie.commodity.sorts.SortByScore;
import ie.exeption.CustomException;
import ie.provider.ProviderManager;
import ie.user.UserManager;

import java.util.*;

public class CommodityManager extends Manager<Commodity> {

    private static CommodityManager instance;
    public ArrayList<String> commoditiesListWithFilters;
    private final JsonHandler<Commodity> jsonMapper;
    public boolean sortByPriceFlag=false;



    @JsonGetter(value = "commoditiesList")

    public static CommodityManager getInstance() {
        if (instance == null) {
            instance = new CommodityManager();
        }
        return instance;
    }
    public CommodityManager () {
        this.commoditiesListWithFilters=null;
        jsonMapper = new CommodityJsonHandler();

    }

    @Override
    public String addElement(Commodity newObject) throws CustomException {
        var objectId = Integer.toString(newObject.getId());
        if (isIdValid(objectId)) {
            throw new CustomException("ObjectAlreadyExists");
        }
        this.objectMap.put(objectId, newObject);
        ProviderManager.getInstance().getElementById(Integer.toString(newObject.getProvideId())).addToCommoditiesList(Integer.toString(newObject.getId()));
        return objectId;
    }

    @Override
    public String updateElement(Commodity newObject) throws CustomException {
        return null;
    }
    public ArrayList<String> addElementsJson(String jsonData) throws JsonProcessingException, CustomException {
        var objectIds = new ArrayList<String>();
        for (var deserializedObject : jsonMapper.deserializeList(jsonData)) {
            objectIds.add(addElement(deserializedObject));
        }
        return objectIds;
    }




    public void buy(String commodityId)throws CustomException{
        if(!isIdValid(commodityId))
            throw new CustomException(Constant.CMD_NOT_FOUND);
        var commodity=objectMap.get(commodityId);
        commodity.buy();
    }

    public void cancelBuying(String commodityId)throws CustomException{
        if(!isIdValid(commodityId))
            throw new CustomException(Constant.CMD_NOT_FOUND);
        var commodity=objectMap.get(commodityId);
        commodity.cancelBuying();
    }



    public float addRate(String commodityId, String username, int rate) throws CustomException {
        if(rate >10 || rate <1)
            throw new CustomException(Constant.OUT_OF_RANGE_RATE);
        if(!isIdValid(String.valueOf(commodityId)))
            throw new CustomException(Constant.CMD_NOT_FOUND);
        if(!UserManager.getInstance().isIdValid(username))
            throw new CustomException(Constant.USR_NOT_FOUND);

        return objectMap.get(commodityId).addRate(username,rate);
    }

    public ArrayList<String> getCommoditiesListByName(String prefix){
        ArrayList <String> commoditiesListByName = new ArrayList<>();
        for (var pair : objectMap.entrySet()) {
            if (pair.getValue().isPrefixOfYourName(prefix))
                commoditiesListByName.add(pair.getKey());
        }
        return commoditiesListByName;
    }

    public ArrayList<String> getCommoditiesByCategory(String category){
        ArrayList <String> commodityByCategory = new ArrayList<>();
            for (var pair : objectMap.entrySet()) {
                if (pair.getValue().isYourCategory(category))
                    commodityByCategory.add(pair.getKey());
            }
        return commodityByCategory;
    }
    public ArrayList<String> getCommoditiesByPrice(float startPrice, float finishPrice){
        ArrayList <String> commodityByCatgory = new ArrayList<>();
        for (var pair : objectMap.entrySet()) {
            if (pair.getValue().isItInYourPriceRange(startPrice,finishPrice))
                commodityByCatgory.add(pair.getKey());
        }
        return commodityByCatgory;
    }
    public void addFilters(String filterName,String value) throws CustomException {
        ArrayList<String> commoditiesId=new ArrayList<>();
        if(filterName.equals(Constant.ActionType.SEARCH_BY_NAME))
            commoditiesId=getCommoditiesListByName(value);
        else if(filterName.equals(Constant.ActionType.SEARCH_BY_CAT))
            commoditiesId=getCommoditiesByCategory(value);
        if(commoditiesListWithFilters==null)
            commoditiesListWithFilters=commoditiesId;
        else{
            commoditiesListWithFilters.retainAll(commoditiesId);
        }

    }
    public void clearFilters(){
        commoditiesListWithFilters=null;
    }
    public void activeSortingByPrice(){
        sortByPriceFlag=true;
    }
    public static ArrayList<String> sortByValue(HashMap<String, Float> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Float> > list =
                new LinkedList<Map.Entry<String, Float> >(hm.entrySet());
        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Float> >() {
            public int compare(Map.Entry<String, Float> o1,
                               Map.Entry<String, Float> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        ArrayList <String> temp=new ArrayList<>();
        for (Map.Entry<String, Float> aa : list) {
            temp.add(aa.getKey());
        }
        return temp;
    }
    public ArrayList<String> calculateSuggestedProducts(String commodityId) throws CustomException {
        var commodity=getElementById(commodityId);
        var commodityCategorys= commodity.getCategories();
        HashMap<String,Float> suggestedCommoditiesId=new HashMap<>();
        for (var pair : objectMap.entrySet()){
            if(String.valueOf(pair.getValue().getId()).equals(commodityId))
                continue;
            if(pair.getValue().isYourCategory(commodityCategorys))
                suggestedCommoditiesId.put(pair.getKey(), 11+ pair.getValue().getRate());
            else
                suggestedCommoditiesId.put(pair.getKey(), pair.getValue().getRate());
        }
        var commodityIdWithTopScores=sortByValue(suggestedCommoditiesId);
        ArrayList<String> fiveCommodityIdWithTopScores=new ArrayList<>();
        for(int i=0;i<Constant.ActionType.TOP_SCORE;i++){
            fiveCommodityIdWithTopScores.add(commodityIdWithTopScores.get(i));
        }
        return fiveCommodityIdWithTopScores;

    }

}
