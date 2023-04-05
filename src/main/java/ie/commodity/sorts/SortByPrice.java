package ie.commodity.sorts;

import ie.commodity.Commodity;

import java.util.Comparator;

public class SortByPrice implements Comparator<Commodity> {
    // Used for sorting in ascending order of
    // roll number

    @Override
    public int compare(Commodity o1, Commodity o2) {
        return (int) ((int)o1.getPrice() - o2.getPrice());
    }
}