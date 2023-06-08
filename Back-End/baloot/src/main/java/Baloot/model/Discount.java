package Baloot.model;

import java.util.HashMap;
import java.util.Map;

public class Discount {
    private final String discountCode;
    private final Integer discountAmount;

    public Discount(String discountCode, int discountAmount){
        this.discountCode = discountCode;
        this.discountAmount = discountAmount;
    }
   public String getDiscountCode(){return this.discountCode;}
   public int getDiscountAmount(){return this.discountAmount;}
   public Map<String,String> getDBTuple(){
        Map<String,String> tuple = new HashMap<>();
        tuple.put("code",this.discountCode);
        tuple.put("amount",this.discountAmount.toString());
        return tuple;
   }
}
