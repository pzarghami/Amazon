package ie.discount;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Discount {
    private String discountCode;
    private int discountAmount;
    @JsonCreator
    private Discount() {
        this.discountAmount=0;
        this.discountCode="";
    }
    @JsonProperty(value = "discountCode", required = true)
    private void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    @JsonProperty(value = "discount", required = true)
    private void setDiscount(int discount) {
        this.discountAmount = discount;
    }

    @JsonGetter(value = "discountCode")
    public String getDiscountCode() {
        return this.discountCode;
    }

    @JsonGetter(value = "discount")
    public int getDiscountAmount() {
        return this.discountAmount;
    }
}
