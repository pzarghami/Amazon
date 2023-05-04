package Baloot.model;

public class Discount {
    private final String discountCode;
    private final int discountAmount;

    public Discount(String discountCode, int discountAmount){
        this.discountCode = discountCode;
        this.discountAmount = discountAmount;
    }
   public String getDiscountCode(){return this.discountCode;}
   public int getDiscountAmount(){return this.discountAmount;}
}
