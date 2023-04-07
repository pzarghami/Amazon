package ie.commodity.sorts;


import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SortByScore implements Comparator<Map.Entry<String, Float>> {


    @Override
    public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
        return (int) (o2.getValue()-o1.getValue());
    }
}
