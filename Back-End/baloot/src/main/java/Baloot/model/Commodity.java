package Baloot.model;

import Baloot.Exeption.CustomException;
import Baloot.model.DTO.CommentDTO;
import Baloot.model.DTO.CommodityBriefDTO;
import Baloot.model.DTO.CommodityDTO;
import Baloot.repository.CommodityRepo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Commodity {


    private String id;
    private String name;
    private Provider provider;
    private float price;
    private ArrayList<String> categories;
    private float firstRate;
    private int inStock;
    private String image;
    private double averageRating;
    private ArrayList<Comment> comments;
    private HashMap<String, Integer> userRateMap;

    public Commodity(String id, String name, Provider provider, float price, ArrayList<String> categories, float rate, int inStock, String image) {
        this.id = id;
        this.name = name;
        this.provider = provider;
        this.price = price;
        this.categories = categories;
        this.firstRate = rate;
        this.averageRating = rate;
        this.inStock = inStock;
        this.image = image;
        this.comments = new ArrayList<>();
        this.userRateMap = new HashMap<>();

    }

    public String getId() {
        return id;
    }

    public float getPrice() {
        return price;
    }

    public Provider getProvider() {
        return this.provider;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public boolean isYourCategory(ArrayList<String> cats) {
        for (String cat : cats) {
            for (String category : categories) {
                if (Objects.equals(category, cat))
                    return true;
            }
        }
        return false;
    }

    public double getAverageRating() {
        return this.averageRating;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }


    public Integer getUserRate(String userId) {
        if (!userRateMap.containsKey(userId)) {
            return null;
        }
        return (Integer) userRateMap.get(userId);
    }

    public void updateCommodityRating(String user, int rate) throws CustomException {
        if (!(1 <= rate && rate <= 10)) {
            throw new CustomException("Invalid rate Score");
        }
        userRateMap.put(user, rate);
        int numberOfRate = userRateMap.size() + 1; // plus one for first rate
        double sumRate = firstRate;
        for (Map.Entry<String, Integer> set : userRateMap.entrySet())
            sumRate += set.getValue();
        averageRating = sumRate / numberOfRate;
    }

    public CommodityDTO getDTO() throws CustomException {
        var DTO = new CommodityDTO();
        DTO.setId(Integer.parseInt(id));
        DTO.setName(name);
        DTO.setProvideName(provider.getName());
        DTO.setPrice(price);
        DTO.setCategories(categories);
        DTO.setRate(averageRating);
        DTO.setInStock(inStock);
        DTO.setImgUrl(image);
        var commentsDTO = new ArrayList<CommentDTO>();
        comments.forEach(comment -> commentsDTO.add(comment.getDTO()));
        DTO.setComments(commentsDTO);
        DTO.setSuggestionCommodity(CommodityRepo.getInstance().calculateSuggestedProducts(id));
        return DTO;
    }

    public CommodityBriefDTO getBriefDTO(int quantity) {
        var DTO = new CommodityBriefDTO();
        DTO.setId(Integer.parseInt(id));
        DTO.setName(name);
        DTO.setPrice(price);
        DTO.setInStock(inStock);
        DTO.setImgUrl(image);
        DTO.setQuantity(quantity);
        DTO.setProviderName(provider.getName());
        return DTO;
    }


}
