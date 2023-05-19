package Baloot.model.DTO;

public class CommodityBriefDTO {
    private int id;
    private String name;
    private float price;
    private int inStock;
    private  String imgUrl;

    public void setId(int id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setPrice(float price) {this.price = price;}
    public void setInStock(int inStock) {this.inStock = inStock;}
    public void setImgUrl(String imgUrl) {this.imgUrl = imgUrl;}
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public float getPrice() {
        return price;
    }
    public int getInStock() {
        return inStock;
    }
    public String getImgUrl() {
        return imgUrl;
    }


}
